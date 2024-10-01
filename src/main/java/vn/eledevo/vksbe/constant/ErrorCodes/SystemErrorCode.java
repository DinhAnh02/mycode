package vn.eledevo.vksbe.constant.ErrorCodes;

import static org.springframework.http.HttpStatus.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatusCode;

public enum SystemErrorCode implements BaseErrorCode {
    INTERNAL_SERVER(INTERNAL_SERVER_ERROR, "500", "Internal Server Error", new HashMap<>()),
    UNAUTHORIZED_SERVER(UNAUTHORIZED, "401", "Unauthorized", new HashMap<>()),
    AUTHENTICATION_SERVER(FORBIDDEN, "403", "Forbidden", new HashMap<>()),
    NOT_FOUND_SERVER(NOT_FOUND, "404", "Not Found", new HashMap<>()),
    BAD_REQUEST_SERVER(BAD_REQUEST, "400", "Bad Request", new HashMap<>()),
    VALIDATE_FORM(UNPROCESSABLE_ENTITY, "422", "Dữ liệu không hợp lệ", new HashMap<>()),
    ORGANIZATION_STRUCTURE(OK, "1000", "Organization Structure Change", new HashMap<>()),
    ;

    private final HttpStatusCode statusCode;
    private final String code; // Đảm bảo `code` là String
    private final String message;
    private final Map<String, String> result; // Đảm bảo `result` là Map<String, String>

    SystemErrorCode(HttpStatusCode statusCode, String code, String message, Map<String, String> result) {
        this.statusCode = statusCode;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    @Override
    public String getCode() { // Trả về kiểu String
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Map<String, String> getResult() { // Đảm bảo `result` là Map<String, String>
        return result;
    }
}
