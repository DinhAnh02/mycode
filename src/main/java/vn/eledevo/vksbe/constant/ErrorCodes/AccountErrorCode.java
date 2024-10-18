package vn.eledevo.vksbe.constant.ErrorCodes;

import static org.springframework.http.HttpStatus.OK;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum AccountErrorCode implements BaseErrorCode {
    NOT_ENOUGH_PERMISSION(OK, "ACC-403", "Bạn không có đủ quyền sử dụng chức năng này", new HashMap<>()),
    ACCOUNT_LIST_EXIT(OK, "ACC-010", "Viện trưởng/Trưởng phòng đã tồn tại", new HashMap<>()),
    ACCOUNT_NOT_FOUND(OK, "ACC-01", "Tài khoản không tồn tại", new HashMap<>()),
    ACCOUNT_ALREADY_EXISTS(OK, "ACC-02", "Tài khoản đã tồn tại", new HashMap<>()),
    INVALID_ACCOUNT_OR_PASSWORD(OK, "ACC-03", "Sai tài khoản hoặc mật khẩu", new HashMap<>()),
    ACCOUNT_TO_BE_LOCKED_IS_LOGGED_IN(
            OK, "ACC-04", "Tài khoản cần khóa trùng với tài khoản đang đăng nhập", new HashMap<>()),
    ACCOUNT_NOT_LINKED_TO_USB(OK, "ACC-05", "Tài khoản chưa được liên kết với USB", new HashMap<>()),
    ACCOUNT_INACTIVE(OK, "ACC-06", "Tài khoản không hoạt động", new HashMap<>()),
    ACCOUNT_ALREADY_ACTIVATED(OK, "ACC-07", "Tài khoản đã được kích hoạt", new HashMap<>()),
    ACCOUNT_NOT_LINKED_TO_COMPUTER(OK, "ACC-08", "Tài khoản chưa được liên kết với máy tính", new HashMap<>()),
    PIN_CODE_MISMATCH(OK, "ACC-09", "Mã PIN nhập lại không trùng khớp", new HashMap<>()),
    OLD_PASSWORD_INCORRECT(OK, "ACC-10", "Mật khẩu cũ không chính xác", new HashMap<>()),
    NEW_PASSWORD_SAME_AS_OLD(OK, "ACC-11", "Mật khẩu mới không được trùng với mật khẩu cũ", new HashMap<>()),
    CONFIRM_PASSWORD_MISMATCH(OK, "ACC-12", "Mật khẩu nhập lại không được trùng với mật khẩu mới", new HashMap<>()),
    AVATAR_SIZE_EXCEEDS_LIMIT(OK, "ACC-13", "Ảnh đại diện không được vượt quá {0}", new HashMap<>()),
    AVATAR_INVALID_FORMAT(OK, "ACC-14", "Ảnh đại diện chỉ chấp nhận định dạng {0}", new HashMap<>()),
    AVATAR_NOT_FOUND(OK, "ACC-15", "Không tìm thấy ảnh đại diện", new HashMap<>()),
    NOT_PERMISSION_CREATE_ACCOUNT_DEPARTMENT_TECH(
            OK, "ACC-16", "Bạn không có quyền thao tác với tài khoản trong phòng ban Kỹ thuật", new HashMap<>()),
    DIRECTORY_NOT_FOUND(OK, "ACC-17", "Không tìm thấy thư mục", new HashMap<>()),
    ACCOUNT_NOT_ACTIVATED(OK, "ACC-18", "Tài khoản chưa được kích hoạt", new HashMap<>()),
    DEPARTMENT_AND_ROLE_INVALID(OK, "ACC-19", "Chức vụ không phù hợp với phòng ban", new HashMap<>()),
    DEPARTMENT_HEAD_INFO_NOT_FOUND(OK, "ACC-20", "Không tìm thấy thông tin trưởng phòng", new HashMap<>()),
    START_TIME_GREATER_THAN_END_TIME(
            OK, "ACC-21", "Thời gian bắt đầu không được lớn hơn thời gian kết thúc", new HashMap<>()),
    DEPARTMENT_CONFLICT(OK, "ACC-22", "Bạn khác phòng ban với tài khoản đang chọn", new HashMap<>()),
    ACCOUNT_LOCK_NOT_FOUND(OK, "ACC-23", "Tài khoản bạn chọn không tồn tại", new HashMap<>()),
    ACCOUNT_IS_LOCK(OK, "ACC-24", "Tài khoản bạn chọn đã bị khóa trước đó ", new HashMap<>()),
    ACCOUNT_ACTIVE(OK, "ACC-25", "Tài khoản đang hoạt", new HashMap<>()),
    CHANGE_FIRST_LOGIN(OK, "ACC-26", "Chức năng này chỉ khả dụng khi bạn đăng nhập lần đầu", new HashMap<>()),
    PATH_AVATAR_NOT_FOUND(OK, "ACC-27", "Đường dẫn avatar không tồn tại", new HashMap<>()),
    CHANGE_PIN_LOGIN(OK, "ACC-28", "Chức năng không khả dụng do tài khoản chưa được tạo mã PIN", new HashMap<>()),
    OLD_PIN_INCORRECT(OK, "ACC-29", "Mã PIN cũ không chính xác", new HashMap<>()),
    NEW_PIN_SAME_AS_OLD(OK, "ACC-30", "Mã PIN mới không được trùng với mã PIN cũ", new HashMap<>()),
    CONFIRM_PIN_MISMATCH(OK, "ACC-31", "Mã PIN nhập lại không được trùng với mã PIN mới", new HashMap<>()),
    URL_NOT_FOUND(OK, "ACC-32", "Đường dẫn ảnh không hợp lệ", new HashMap<>()),
    ACCOUNT_LINKED_USB(OK, "ACC-33", "Tài khoản đã được liên kết usb . Vui lòng gỡ usb cũ và thử lại", new HashMap<>()),
    ACCOUNT_SWAP_EXITS(OK, "ACC-34", "Tài khoản bị hoán đổi không tồn tại", new HashMap<>()),
    ACCOUNT_INVALID(OK, "ACC-35", "Tài khoản không hợp lệ. Vui lòng liên hệ IT Admin để được hỗ trợ", new HashMap<>()),
    NOT_PERMISSION_CREATE_ACCOUNT(OK, "ACC-36", "Bạn không có quyền tạo tài khoản IT Admin", new HashMap<>()),
    ACCOUNT_NOT_READ_DATA_DEPARTMENT(OK, "ACC-37", "Bạn không có quyền xem dữ liệu của phòng ban khác", new HashMap<>()),
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
            if (object instanceof HashMap) {
                HashMap<?, ?> map = (HashMap<?, ?>) object;
                map.forEach((key, val) -> {
                    this.result.put(key.toString(), Optional.ofNullable(val));
                });
            }
        }
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
