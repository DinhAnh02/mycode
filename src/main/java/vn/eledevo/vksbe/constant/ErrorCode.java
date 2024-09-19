package vn.eledevo.vksbe.constant;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(INTERNAL_SERVER_ERROR, 500, "INTERNAL_SERVER_ERROR"),
    FIELD_INVALID(UNPROCESSABLE_ENTITY, 1001, "Các trường không hợp lệ!"),
    METHOD_ERROR(METHOD_NOT_ALLOWED, 1002, "Phương thức không hợp lệ!"),
    EX_NOT_FOUND(NOT_FOUND, 1008, "Không tìm thấy bản ghi"),
    RECORD_EXIST(CONFLICT, 1010, "Bản ghi đã tồn tại"),
    USER_EXIST(CONFLICT, 1010, "Tài khoản đã tồn tại"),
    USER_NOT_EXIST(NOT_FOUND, 1008, "Tài khoản không tồn tại hoặc đã bị xóa trước đó"),
    DEVICE_NOT_EXIST(NOT_FOUND, 1008, "Thiết bị không tồn tại hoặc đã bị xóa trước đó"),
    PASSWORD_FAILURE(OK, 8000, "Sai tài khoản hoặc mật khẩu"),
    KEY_USB_NOT_FOUND(OK, 7000, "Thu hồi thất bại do key usb chưa được tạo cho kết nối này. Vui lòng kểm tra lại"),
    CHECK_USB(CONFLICT, 6000, "Usb không chính xác"),
    ACCOUNT_NOT_FOUND(NOT_FOUND, 4040, " Acount không tồn tại trong hệ thống"),
    COMPUTER_NOT_FOUND(NOT_FOUND, 4040, " Computer không tồn tại trong hệ thống"),
    COMPUTER_NOT_CONNECT_TO_ACCOUNT(NOT_FOUND,404,"Máy tính chưa được liên kết với tài khoản"),
    CHECK_FROM_DATE(UNPROCESSABLE_ENTITY, 4220, "Thời gian bắt đầu không được lớn hơn thời gian kết thúc."),
    CHECK_ORGANIZATIONAL_STRUCTURE(CONFLICT,4090,"Cơ cấu tổ chức đã thay đổi. Vui lòng đăng nhập lại để có dữ liệu mới nhất."),
    DUPLICATE_ACCOUNT(BAD_REQUEST,5000, "Tài khoản cần khóa trùng với tài khoản đang đăng nhập"),
    ACCOUNT_NOT_LOCK(FORBIDDEN, 4030, "Bạn không được phép cập nhật tài khoản này");

    ErrorCode(HttpStatusCode statusCode, int code, String message) {
        this.statusCode = statusCode;
        this.code = code;
        this.message = message;
    }

    private HttpStatusCode statusCode;
    private int code;
    private String message;
}
