package vn.eledevo.vksbe.constant.ErrorCodes;

import static org.springframework.http.HttpStatus.OK;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatusCode;

public enum ComputerErrorCode implements BaseErrorCode {
    PC_NOT_FOUND(OK, "PC-01", "Thiết bị máy tính không tồn tại", new HashMap<>()),
    PC_NOT_LINKED_TO_ACCOUNT(OK, "PC-02", "Máy tính chưa được liên kết với tài khoản", new HashMap<>()),
    PC_CODE_ALREADY_EXISTS(OK, "PC-03", "Mã máy tính đã tồn tại trong hệ thống", new HashMap<>()),
    PC_NAME_ALREADY_EXISTS(OK, "PC-04", "Tên máy tính đã tồn tại trong hệ thống", new HashMap<>()),
    ;

    private final HttpStatusCode statusCode;
    private final String code; // Đảm bảo `code` là String
    private final String message;
    private final Map<String, Optional<?>> result;

    ComputerErrorCode(HttpStatusCode statusCode, String code, String message, Map<String, Optional<?>> result) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
        this.result = result;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setResult(Optional<?> value) {

    }

    @Override
    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    @Override
    public Map<String, Optional<?>> getResult() {
        return result;
    }
}
