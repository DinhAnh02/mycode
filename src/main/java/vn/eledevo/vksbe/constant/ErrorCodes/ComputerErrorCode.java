package vn.eledevo.vksbe.constant.ErrorCodes;

import org.springframework.http.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

public enum ComputerErrorCode implements BaseErrorCode{
    PC_NOT_FOUND(OK, "PC-01", "Thiết bị máy tính không tồn tại", new HashMap<>()),
    PC_NOT_LINKED_TO_ACCOUNT(OK, "PC-02", "Máy tính chưa được liên kết với tài khoản", new HashMap<>()),
    PC_ALREADY_EXISTS(OK, "PC-03", "Thiết bị đã tồn tại trong hệ thống", new HashMap<>()),
    PC_NAME_ALREADY_EXISTS(OK, "PC-04", "Tên thiết bị đã tồn tại trong hệ thống", new HashMap<>());
    ;
    private final HttpStatusCode statusCode;
    private final String code;  // Đảm bảo `code` là String
    private final String message;
    private final Map<String, String> result;
    ComputerErrorCode(HttpStatusCode statusCode, String code, String message, Map<String,String> result) {
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
    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    @Override
    public Map<String, String> getResult() {
        return result;
    }
}
