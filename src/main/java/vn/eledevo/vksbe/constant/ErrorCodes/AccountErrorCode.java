package vn.eledevo.vksbe.constant.ErrorCodes;

import static org.springframework.http.HttpStatus.OK;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum AccountErrorCode implements BaseErrorCode {
    NOT_ENOUGH_PERMISSION(OK, "TK-403", "Bạn không có đủ quyền sử dụng chức năng này", new HashMap<>()),
    ACCOUNT_NOT_FOUND(OK, "TK-01", "Tài khoản không tồn tại", new HashMap<>()),
    ACCOUNT_ALREADY_EXISTS(OK, "TK-02", "Tài khoản đã tồn tại", new HashMap<>()),
    INVALID_ACCOUNT_OR_PASSWORD(OK, "TK-03", "Sai tài khoản hoặc mật khẩu", new HashMap<>()),
    ACCOUNT_TO_BE_LOCKED_IS_LOGGED_IN(
            OK, "TK-04", "Tài khoản cần khóa trùng với tài khoản đang đăng nhập", new HashMap<>()),
    ACCOUNT_NOT_LINKED_TO_USB(OK, "TK-05", "Tài khoản chưa được liên kết với USB", new HashMap<>()),
    ACCOUNT_INACTIVE(OK, "TK-06", "Tài khoản không hoạt động", new HashMap<>()),
    ACCOUNT_ALREADY_ACTIVATED(OK, "TK-07", "Tài khoản đã được kích hoạt", new HashMap<>()),
    ACCOUNT_NOT_LINKED_TO_COMPUTER(OK, "TK-08", "Tài khoản chưa được liên kết với máy tính", new HashMap<>()),
    PIN_CODE_MISMATCH(OK, "TK-09", "Mã PIN nhập lại không trùng khớp", new HashMap<>()),
    OLD_PASSWORD_INCORRECT(OK, "TK-10", "Mật khẩu cũ không chính xác", new HashMap<>()),
    NEW_PASSWORD_SAME_AS_OLD(OK, "TK-11", "Mật khẩu mới không được trùng với mật khẩu cũ", new HashMap<>()),
    CONFIRM_PASSWORD_MISMATCH(OK, "TK-12", "Mật khẩu nhập lại không được trùng với mật khẩu mới", new HashMap<>()),
    AVATAR_SIZE_EXCEEDS_LIMIT(OK, "TK-13", "Ảnh đại diện không được vượt quá {0}", new HashMap<>()),
    AVATAR_INVALID_FORMAT(OK, "TK-14", "Ảnh đại diện chỉ chấp nhận định dạng {0}", new HashMap<>()),
    AVATAR_NOT_FOUND(OK, "TK-15", "Không tìm thấy ảnh đại diện", new HashMap<>()),
    POSITION_NOT_FOUND(OK, "TK-16", "Chức vụ không tồn tại", new HashMap<>()),
    DIRECTORY_NOT_FOUND(OK, "TK-17", "Không tìm thấy thư mục", new HashMap<>()),
    ACCOUNT_NOT_ACTIVATED(OK, "TK-18", "Tài khoản chưa được kích hoạt", new HashMap<>()),
    CURRENT_POSITION_NOT_CHANGEABLE(OK, "TK-19", "Chức vụ hiện tại không thể thay đổi", new HashMap<>()),
    DEPARTMENT_HEAD_INFO_NOT_FOUND(OK, "TK-20", "Không tìm thấy thông tin trưởng phòng", new HashMap<>()),
    START_TIME_GREATER_THAN_END_TIME(
            OK, "TK-21", "Thời gian bắt đầu không được lớn hơn thời gian kết thúc", new HashMap<>()),
    DEPARTMENT_CONFLICT(OK, "TK-22", "Bạn khác phòng ban với tài khoản đang chọn", new HashMap<>()),
    ACCOUNT_LOCK_NOT_FOUND(OK, "TK-23", "Tài khoản bạn chọn không tồn tại", new HashMap<>()),
    ACCOUNT_IS_LOCK(OK, "TK-24", "Tài khoản bạn chọn đã bị khóa trước đó ", new HashMap<>()),
    ACCOUNT_ACTIVE(OK, "TK-25", "Tài khoản đang hoạt", new HashMap<>()),
    CHANGE_PWD_LOGIN(OK, "TK-26", "Chức năng này chỉ khả dụng khi bạn đăng nhập lần đầu", new HashMap<>()),
    ;

    private final HttpStatusCode statusCode;
    private final String code; // Đảm bảo `code` là String
    private final String message;
    private final Map<String, String> result;

    AccountErrorCode(HttpStatusCode statusCode, String code, String message, Map<String, String> result) {
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
