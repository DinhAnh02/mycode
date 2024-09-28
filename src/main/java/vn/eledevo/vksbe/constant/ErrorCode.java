package vn.eledevo.vksbe.constant;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(INTERNAL_SERVER_ERROR, 500, "INTERNAL_SERVER_ERROR", new HashMap<>()),
    FIELD_INVALID(UNPROCESSABLE_ENTITY, 4200, "Lỗi validate không hợp lệ!", new HashMap<>()),
    METHOD_ERROR(METHOD_NOT_ALLOWED, 1002, "Phương thức không hợp lệ!", new HashMap<>()),
    EX_NOT_FOUND(NOT_FOUND, 1008, "Không tìm thấy bản ghi", new HashMap<>()),
    RECORD_EXIST(CONFLICT, 1010, "Bản ghi đã tồn tại", new HashMap<>()),
    USER_EXIST(CONFLICT, 1010, "Tài khoản đã tồn tại", new HashMap<>()),
    USER_NOT_EXIST(UNAUTHORIZED, 1008, "Tài khoản không tồn tại ", new HashMap<>()),
    DEVICE_NOT_EXIST(NOT_FOUND, 1008, "Thiết bị không tồn tại hoặc đã bị xóa trước đó", new HashMap<>()),
    PASSWORD_FAILURE(OK, 8000, "Sai tài khoản hoặc mật khẩu", new HashMap<>()),
    KEY_USB_NOT_FOUND(OK, 7000, "Thu hồi thất bại do key usb chưa được tạo cho kết nối này. Vui lòng kiểm tra lại", new HashMap<>()),
    CHECK_USB(CONFLICT, 6000, "Usb không chính xác", new HashMap<>()),
    ACCOUNT_NOT_FOUND(NOT_FOUND, 4040, "Acount không tồn tại trong hệ thống", new HashMap<>()),
    COMPUTER_NOT_FOUND(NOT_FOUND, 4040, "Computer không tồn tại trong hệ thống", new HashMap<>()),
    COMPUTER_NOT_CONNECT_TO_ACCOUNT(NOT_FOUND, 404, "Máy tính chưa được liên kết với tài khoản", new HashMap<>()),
    COMPUTER_HAS_EXISTED(NOT_FOUND, 4000, "Thiết bị đã tồn tại trong hệ thống", new HashMap<>()),
    NAME_COMPUTER_HAS_EXISTED(NOT_FOUND, 4000, "Tên Thiết bị đã tồn tại trong hệ thống", new HashMap<>()),
    CHECK_FROM_DATE(UNPROCESSABLE_ENTITY, 4220, "Thời gian bắt đầu không được lớn hơn thời gian kết thúc.", new HashMap<>()),
    CHECK_ORGANIZATIONAL_STRUCTURE(CONFLICT, 4090, "Cơ cấu tổ chức đã thay đổi. Vui lòng đăng nhập lại để có dữ liệu mới nhất.", new HashMap<>()),
    DUPLICATE_ACCOUNT(BAD_REQUEST, 5000, "Tài khoản cần khóa trùng với tài khoản đang đăng nhập", new HashMap<>()),
    ACCOUNT_NOT_LOCK(FORBIDDEN, 4030, "Bạn không được phép cập nhật tài khoản này", new HashMap<>()),
    ACCOUNT_NOT_CONNECT_USB(NOT_FOUND, 4041, "Tài khoản chưa được liên kết với USB", new HashMap<>()),
    ACCOUNT_NOT_STATUS_ACTIVE(CONFLICT, 4091, "Tài khoản không hoạt động", new HashMap<>()),
    TOKEN_EXPIRE(UNAUTHORIZED, 4010, "Hết phiên đăng nhập", new HashMap<>()),
    CONFLICT_USB(CONFLICT, 4090, "USB không phải của tài khoản này. Vui lòng kiểm tra lại", new HashMap<>()),
    ACCOUNT_STATUS_ACTIVE(UNPROCESSABLE_ENTITY, 4222, "Tài khoản đã được kích hoạt ", new HashMap<>()),
    ACCOUNT_NOT_CONNECT_COMPUTER(UNPROCESSABLE_ENTITY, 4223, "Tài khoản chưa được liên kết với máy tính", new HashMap<>()),
    ACCOUNT_NOT_PIN(BAD_REQUEST, 4000, "Mã pin nhập lại không trùng khớp", new HashMap<>()),
    OLD_PASSWORD_FAILURE(BAD_REQUEST, 4000, "Mật khẩu cũ không chính xác", new HashMap<>()),
    NEW_PASSWORD_FAILURE(BAD_REQUEST, 4000, "Mật khẩu mới không được giống mật khẩu cũ", new HashMap<>()),
    CONFIRM_PASSWORD_FAILURE(BAD_REQUEST, 4000, "Mật khẩu nhập lại không giống mật khẩu mới", new HashMap<>()),
    PERMISSION_DENIED(FORBIDDEN, 4003, "Bạn không có quyền thực hiện hành động này", new HashMap<>()),
    AVATAR_EMPTY(BAD_REQUEST, 4005, "Hãy chọn ảnh đại diện", new HashMap<>()),
    AVATAR_MAX_SIZE(BAD_REQUEST, 4006, "Ảnh đại diện không được vượt quá {0} MB.", new HashMap<>()),
    AVATAR_EXTENSION_INVALID(BAD_REQUEST, 4007, "Ảnh đại diện chỉ chấp nhận định dạng {0}.", new HashMap<>()),
    AVATAR_NOT_FOUND(NOT_FOUND, 4042, "Không tìm thấy ảnh đại diện", new HashMap<>()),
    ROLE_NOT_FOUND(NOT_FOUND, 1101, "Chức vụ không tồn tại", new HashMap<>()),
    FOLDER_NOT_FOUND(NOT_FOUND, 1011, "Không tìm thấy thư mục.", new HashMap<>()),
    CHECK_ACTIVE_ACCOUNT(UNAUTHORIZED, 6969, "Tài khoản chưa được kích hoạt", new HashMap<>()),
    UNAUTHORIZED_ACTIVE_ACCOUNT(OK, 403, "Bạn không có quyền kích hoạt tài khoản.", new HashMap<>()),
    ROLE_NOT_TRUE(BAD_REQUEST, 4000, "Chức vụ hiện tại không thể thay đổi", new HashMap<>()),
    LEADER_NOT_FOUND(NOT_FOUND, 4030, "Không tìm thấy thông tin trưởng phòng", new HashMap<>());

    ErrorCode(HttpStatusCode statusCode, int code, String message, HashMap<?, ?> result) {
        this.statusCode = statusCode;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    private HttpStatusCode statusCode;
    private int code;
    private String message;
    private HashMap<?, ?> result;
}
