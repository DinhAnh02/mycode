package vn.eledevo.vksbe.constant.ErrorCodes;

import org.springframework.http.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

public enum RoleErrorCode implements BaseErrorCode{
    ROLE_NOT_FOUND(OK, "TK-16", "Chức vụ không tồn tại", new HashMap<>()),
    CURRENT_ROLE_NOT_CHANGEABLE(OK, "TK-19", "Chức vụ hiện tại không thể thay đổi", new HashMap<>()),
    ;

    private final HttpStatusCode statusCode;
    private final String code; // Đảm bảo `code` là String
    private final String message;
    private final Map<String, Optional<?>> result;

    RoleErrorCode(HttpStatusCode statusCode, String code, String message, Map<String, Optional<?>> result) {
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
