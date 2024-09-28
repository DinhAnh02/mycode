package vn.eledevo.vksbe.constant.ErrorCodes;

import org.springframework.http.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

public enum UsbErrorCode implements BaseErrorCode{
    USB_KEY_NOT_FOUND(OK, "USB-01", "Thu hồi thất bại do key usb chưa được tạo cho kết nối này. Vui lòng kiểm tra lại", new HashMap<>()),
    USB_NOT_BELONG_TO_ACCOUNT(OK, "USB-02", "USB không phải của tài khoản này. Vui lòng kiểm tra lại", new HashMap<>()),
    ;
    private final HttpStatusCode statusCode;
    private final String code;  // Đảm bảo `code` là String
    private final String message;
    private final Map<String, String> result;
    UsbErrorCode(HttpStatusCode statusCode, String code, String message, Map<String,String> result) {
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
