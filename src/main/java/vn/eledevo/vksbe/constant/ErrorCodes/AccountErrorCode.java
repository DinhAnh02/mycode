package vn.eledevo.vksbe.constant.ErrorCodes;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

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
    CHANGE_FIRST_LOGIN(OK, "TK-26", "Chức năng này chỉ khả dụng khi bạn đăng nhập lần đầu", new HashMap<>()),
    PATH_AVATAR_NOT_FOUND(OK, "TK-27", "Không tồn tại đường dẫn avatar ", new HashMap<>()),
    CHANGE_PIN_LOGIN(OK, "TK-28", "Chức năng không khả dụng do tài khoản chưa được tạo mã PIN", new HashMap<>()),
    OLD_PIN_INCORRECT(OK, "TK-29", "Mã PIN cũ không chính xác", new HashMap<>()),
    NEW_PIN_SAME_AS_OLD(OK, "TK-30", "Mã PIN mới không được trùng với mã PIN cũ", new HashMap<>()),
    CONFIRM_PIN_MISMATCH(OK, "TK-31", "Mã PIN nhập lại không được trùng với mã PIN mới", new HashMap<>()),
    URL_NOT_FOUND(OK, "TK-32", "Đường dẫn ảnh không hợp lệ", new HashMap<>()),
    ACCOUNT_LINKED_USB(OK,"TK-33","Tài khoản đã được liên kết usb . Vui lòng gỡ usb cũ và thử lại",new HashMap<>()),
    ACCOUNT_LIST_EXIT(OK,"TK-010","Viện trưởng/Trưởng phòng đã tồn tại",new HashMap<>()),
    ACCOUNT_SWAP_EXITS(OK, "TK-34", "Tài khoản bị hoán đổi không tồn tại", new HashMap<>()),
    ACCOUNT_INVALID(OK, "TK-35", "Tài khoản không hợp lệ. Vui lòng liên hệ IT Admin để được hỗ trợ", new HashMap<>()),
    NOT_PERMISSION_CREATE_ACCOUNT(OK, "TK-36", "Bạn không có quyền tạo tài khoản IT Admin", new HashMap<>()),
    ACCOUNT_NOT_READ_DATA_DEPARTMENT(OK, "TK-37", "Bạn không có quyền xem dữ liệu của phòng ban khác", new HashMap<>()),
    CHECK_FROM_DATE(OK, "TK-38", "Thời gian bắt đầu không được lớn hơn thời gian kết thúc.", new HashMap<>()),
    ;

    private final HttpStatusCode statusCode;
    private final String code; // Đảm bảo `code` là String
    private final String message;
    private final Map<String, Optional<?>> result;

    AccountErrorCode(HttpStatusCode statusCode, String code, String message, Map<String, Optional<?>> result) {
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
    public Map<String, Optional<?>> getResult() {
        return result;
    }

    @Override
    public void setResult(Optional<?> value) {
        // Kiểm tra nếu Optional chứa giá trị
        if (value.isPresent()) {
            Object object = value.get();
            // Sử dụng reflection để lấy tất cả các trường (fields) của object
            Field[] fields = object.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true); // Cho phép truy cập vào các trường private

                try {
                    // Lấy tên trường (field name) làm key
                    String key = field.getName();
                    // Lấy giá trị của trường (field value) làm value và gán vào result
                    Object fieldValue = field.get(object);
                    this.result.put(key, Optional.ofNullable(fieldValue)); // Sử dụng Optional để bọc giá trị
                } catch (IllegalAccessException e) {
                    e.printStackTrace(); // Xử lý ngoại lệ nếu không thể truy cập vào trường
                }
            }
        }
    }
}
