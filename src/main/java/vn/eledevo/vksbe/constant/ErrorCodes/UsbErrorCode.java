package vn.eledevo.vksbe.constant.ErrorCodes;

import static org.springframework.http.HttpStatus.OK;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatusCode;

public enum UsbErrorCode implements BaseErrorCode {
    USB_KEY_NOT_FOUND(
            OK,
            "USB-01",
            "Thu hồi thất bại do key usb chưa được tạo cho kết nối này. Vui lòng kiểm tra lại",
            new HashMap<>()),
    USB_NOT_BELONG_TO_ACCOUNT(OK, "USB-02", "USB không phải của tài khoản này. Vui lòng kiểm tra lại", new HashMap<>()),
    FOLDER_NOT_FOUND(OK, "USB-03", "Không tìm thấy thư mục.", new HashMap<>()),
    USB_NOT_FOUND(OK,"USB-04","USB không tồn tại",new HashMap<>()),
    USB_IS_CONNECTED(OK,"USB-05","USB đã được kết nối với tài khoản khác",new HashMap<>())
    ;
    private final HttpStatusCode statusCode;
    private final String code; // Đảm bảo `code` là String
    private final String message;
    private final Map<String, Optional<?>> result;

    UsbErrorCode(HttpStatusCode statusCode, String code, String message, Map<String, Optional<?>> result) {
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
