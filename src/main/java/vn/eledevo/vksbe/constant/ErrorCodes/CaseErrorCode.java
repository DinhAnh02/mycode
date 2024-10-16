package vn.eledevo.vksbe.constant.ErrorCodes;

import static org.springframework.http.HttpStatus.OK;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum CaseErrorCode implements BaseErrorCode {
    CASE_NOT_FOUND(OK, "CS-01", "Vụ án không tồn tại", new HashMap<>()),
    ;

    private final HttpStatusCode statusCode;
    private final String code; // Đảm bảo `code` là String
    private final String message;
    private final Map<String, Optional<?>> result;

    CaseErrorCode(HttpStatusCode statusCode, String code, String message, Map<String, Optional<?>> result) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
        this.result = result;
    }

    @Override
    public void setResult(Optional<?> value) {
        this.result.put(code, value);
    }
}
