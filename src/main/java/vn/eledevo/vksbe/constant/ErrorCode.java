package vn.eledevo.vksbe.constant;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(INTERNAL_SERVER_ERROR, 500, "INTERNAL_SERVER_ERROR"),
    FIELD_INVALID(UNPROCESSABLE_ENTITY, 4200, "Lỗi validate không hợp lệ!"),
    METHOD_ERROR(METHOD_NOT_ALLOWED, 1002, "Phương thức không hợp lệ!"),
    EX_NOT_FOUND(NOT_FOUND, 1008, "Không tìm thấy bản ghi"),
    RECORD_EXIST(CONFLICT, 1010, "Bản ghi đã tồn tại"),
    USER_EXIST(CONFLICT, 1010, "Tài khoản đã tồn tại"),
    USER_NOT_EXIST(UNAUTHORIZED, 1008, "Tài khoản không tồn tại "),
    DEVICE_NOT_EXIST(NOT_FOUND, 1008, "Thiết bị không tồn tại hoặc đã bị xóa trước đó"),
    PASSWORD_FAILURE(OK, 8000, "Sai tài khoản hoặc mật khẩu"),
    KEY_USB_NOT_FOUND(OK, 7000, "Thu hồi thất bại do key usb chưa được tạo cho kết nối này. Vui lòng kểm tra lại"),
    CHECK_USB(CONFLICT, 6000, "Usb không chính xác"),
    ACCOUNT_NOT_FOUND(NOT_FOUND, 4040, " Acount không tồn tại trong hệ thống"),
    COMPUTER_NOT_FOUND(NOT_FOUND, 4040, "Computer không tồn tại trong hệ thống"),
    COMPUTER_NOT_CONNECT_TO_ACCOUNT(NOT_FOUND, 404, "Máy tính chưa được liên kết với tài khoản"),
    COMPUTER_HAS_EXISTED(NOT_FOUND, 4000, " Thiết bị đã tồn tại trong hệ thống"),
    NAME_COMPUTER_HAS_EXISTED(NOT_FOUND, 4000, "Tên Thiết bị đã tồn tại trong hệ thống"),
    CHECK_FROM_DATE(UNPROCESSABLE_ENTITY, 4220, "Thời gian bắt đầu không được lớn hơn thời gian kết thúc."),
    CHECK_ORGANIZATIONAL_STRUCTURE(
            CONFLICT, 4090, "Cơ cấu tổ chức đã thay đổi. Vui lòng đăng nhập lại để có dữ liệu mới nhất."),
    DUPLICATE_ACCOUNT(BAD_REQUEST, 5000, "Tài khoản cần khóa trùng với tài khoản đang đăng nhập"),
    ACCOUNT_NOT_LOCK(FORBIDDEN, 4030, "Bạn không được phép cập nhật tài khoản này"),
    ACCOUNT_NOT_CONNECT_USB(NOT_FOUND, 4041, "Tài khoản chưa được liên kết với USB"),
    ACCOUNT_NOT_STATUS_ACTIVE(CONFLICT, 4091, "Tài khoản không hoạt động"),
    TOKEN_EXPIRE(UNAUTHORIZED, 4010, "Hết phiên đăng nhập"),
    CONFLICT_USB(CONFLICT, 4090, "USB không phải của tài khoản này. Vui lòng kiểm tra lại"),
    ACCOUNT_STATUS_ACTIVE(UNPROCESSABLE_ENTITY, 4222, "Tài khoản đã được kích hoạt "),
    ACCOUNT_NOT_CONNECT_COMPUTER(UNPROCESSABLE_ENTITY, 4223, "Tài khoản chưa được liên kết với máy tính"),
    ACCOUNT_NOT_PIN(BAD_REQUEST, 4000, "Mã pin nhập lại không trùng khớp"),
    OLD_PASSWORD_FAILURE(BAD_REQUEST, 4000, "Mật khẩu cũ không chính xác"),
    NEW_PASSWORD_FAILURE(BAD_REQUEST, 4000, "Mật khẩu mới không được giống mật khẩu cũ"),
    CONFIRM_PASSWORD_FAILURE(BAD_REQUEST, 4000, "Mật khẩu nhập lại không giống mật khẩu mới"),
    PERMISSION_DENIED(FORBIDDEN, 4003, "Bạn không có quyền thực hiện hành động này"),
    AVATAR_EMPTY(BAD_REQUEST, 4005, "Hãy chọn ảnh đại diện"),
    AVATAR_MAX_SIZE(BAD_REQUEST, 4006, "Ảnh đại diện không được vượt quá {0} MB."),
    AVATAR_EXTENSION_INVALID(BAD_REQUEST, 4007, "Ảnh đại diện chỉ chấp nhận định dạng {0}."),
    AVATAR_NOT_FOUND(NOT_FOUND, 4042, "Không tìm thấy ảnh đại diện"),
    ROLE_NOT_FOUND(NOT_FOUND, 1101, "Chức vụ không tồn tại"),
    FOLDER_NOT_FOUND(NOT_FOUND, 1011, "Không tìm thấy thư mục."),
    CHECK_ACTIVE_ACCOUNT(UNAUTHORIZED, 6969, "Tài khoản chưa được kích hoạt");

    ErrorCode(HttpStatusCode statusCode, int code, String message) {
        this.statusCode = statusCode;
        this.code = code;
        this.message = message;
    }

    private HttpStatusCode statusCode;
    private int code;
    private String message;
}
