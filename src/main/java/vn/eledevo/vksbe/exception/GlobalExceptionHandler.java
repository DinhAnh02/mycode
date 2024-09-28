package vn.eledevo.vksbe.exception;

import static org.springframework.http.HttpStatus.*;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import vn.eledevo.vksbe.constant.ErrorCodes.BaseErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.SystemErrorCode;
import vn.eledevo.vksbe.dto.response.ApiResponse;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Phương thức tạo ResponseEntity từ SystemErrorCode
    private ResponseEntity<Object> generateExceptionResponse(BaseErrorCode errorCode, Exception ex) {
        if (errorCode.getStatusCode() == INTERNAL_SERVER_ERROR) {
            log.error(ex.getMessage(), ex);
        }
        String displayMessage = (ex instanceof ApiException) ? ex.getMessage() : errorCode.getMessage();

        // Lấy kết quả với kiểu dữ liệu `Map<String, String>`
        Map<String, String> result = errorCode.getResult() instanceof Map ? (Map<String, String>) errorCode.getResult() : new HashMap<>();

        return ResponseEntity.status(errorCode.getStatusCode()).body(new HashMap<String, Object>() {
            {
                put("result", result);
                put("message", displayMessage);
                put("code", errorCode.getCode());  // Đảm bảo `code` là String
            }
        });
    }


    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException ex) {
        return generateExceptionResponse(ex.getCode(), ex);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        Map<String, String> errors = ex.getErrors();
        ApiResponse<Map<String, String>> response =
                new ApiResponse<>(Integer.parseInt(SystemErrorCode.BAD_REQUEST_SERVER.getCode()), SystemErrorCode.BAD_REQUEST_SERVER.getMessage(), errors);
        return ResponseEntity.status(OK).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        return generateExceptionResponse(SystemErrorCode.INTERNAL_SERVER, ex);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleNoSupportedException(HttpRequestMethodNotSupportedException ex) {
        return generateExceptionResponse(SystemErrorCode.UNAUTHORIZED_SERVER, ex);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleNotReadableException(Exception ex) {
        return generateExceptionResponse(SystemErrorCode.BAD_REQUEST_SERVER, ex);
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    public ResponseEntity<Object> handleThrowableException(Exception ex) {
        return generateExceptionResponse(SystemErrorCode.INTERNAL_SERVER, ex);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(Exception ex) {
        return generateExceptionResponse(SystemErrorCode.BAD_REQUEST_SERVER, ex);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleValidationExceptions(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        ApiResponse<?> response = new ApiResponse<>(Integer.parseInt(SystemErrorCode.BAD_REQUEST_SERVER.getCode()), SystemErrorCode.BAD_REQUEST_SERVER.getMessage(), errors);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
        });
        ApiResponse<?> response =
                new ApiResponse<>(UNPROCESSABLE_ENTITY.value(), UNPROCESSABLE_ENTITY.getReasonPhrase(), errors);
        return ResponseEntity.ok(response);
    }
}