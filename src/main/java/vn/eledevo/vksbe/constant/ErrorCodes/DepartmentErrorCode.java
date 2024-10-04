package vn.eledevo.vksbe.constant.ErrorCodes;

import org.springframework.http.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

public enum DepartmentErrorCode implements BaseErrorCode{
    DEPARTMENT_NOT_FOUND(OK, "DPM-01", "Phòng ban không tồn tại", new HashMap<>()),
    ;

    private final HttpStatusCode statusCode;
    private final String code; // Đảm bảo `code` là String
    private final String message;
    private final Map<String, Optional<?>> result;

    DepartmentErrorCode(HttpStatusCode statusCode, String code, String message, Map<String, Optional<?>> result) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
        this.result = result;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return null;
    }

    @Override
    public Map<String, Optional<?>> getResult() {
        return Map.of();
    }

    @Override
    public String getCode() {
        return "";
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public void setResult(Optional<?> value) {

    }
}
