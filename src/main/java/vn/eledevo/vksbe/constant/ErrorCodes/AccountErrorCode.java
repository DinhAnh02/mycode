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
    ACCOUNT_LIST_EXIT(OK, "ACC-010", "Viện trưởng/Trưởng phòng đã tồn tại", new HashMap<>()),
    ACCOUNT_NOT_FOUND(OK, "ACC-01", "Tài khoản không tồn tại", new HashMap<>()),
    ACCOUNT_ALREADY_EXISTS(OK, "ACC-02", "Tài khoản đã tồn tại", new HashMap<>()),
    INVALID_ACCOUNT_OR_PASSWORD(OK, "ACC-03", "Sai mật khẩu", new HashMap<>()),
    ACCOUNT_NOT_LINKED_TO_USB(OK, "ACC-04", "Tài khoản chưa được liên kết với USB", new HashMap<>()),
    ACCOUNT_INACTIVE(OK, "ACC-05", "Tài khoản không hoạt động", new HashMap<>()),
    ACCOUNT_NOT_LINKED_TO_COMPUTER(OK, "ACC-06", "Tài khoản chưa được liên kết với máy tính", new HashMap<>()),
    PIN_CODE_MISMATCH(OK, "ACC-07", "Mã PIN nhập lại không trùng khớp", new HashMap<>()),
    OLD_PASSWORD_INCORRECT(OK, "ACC-08", "Mật khẩu cũ không chính xác", new HashMap<>()),
    NEW_PASSWORD_SAME_AS_OLD(OK, "ACC-09", "Mật khẩu mới không được trùng với mật khẩu cũ", new HashMap<>()),
    CONFIRM_PASSWORD_MISMATCH(OK, "ACC-10", "Mật khẩu nhập lại phải giống mật khẩu mới", new HashMap<>()),
    AVATAR_SIZE_EXCEEDS_LIMIT(OK, "ACC-11", "Ảnh đại diện không được vượt quá {0}", new HashMap<>()),
    AVATAR_INVALID_FORMAT(OK, "ACC-12", "Chỉ chấp nhận ảnh có các định dạng {0}", new HashMap<>()),
    DEPARTMENT_CONFLICT(OK, "ACC-13", "Cán bộ đã bị điều chuyển hoặc tài khoản đang bị khoá. Vui lòng kiểm tra lại thông tin.", new HashMap<>()),
    CHANGE_FIRST_LOGIN(OK, "ACC-14", "Chức năng này chỉ khả dụng khi bạn đăng nhập lần đầu", new HashMap<>()),
    CHANGE_PIN_LOGIN(OK, "ACC-15", "Chức năng không khả dụng do tài khoản chưa được tạo mã PIN", new HashMap<>()),
    OLD_PIN_INCORRECT(OK, "ACC-16", "Mã PIN cũ không chính xác", new HashMap<>()),
    NEW_PIN_SAME_AS_OLD(OK, "ACC-17", "Mã PIN mới không được trùng với mã PIN cũ", new HashMap<>()),
    CONFIRM_PIN_MISMATCH(OK, "ACC-18", "Mã PIN nhập lại phải giống với mã PIN mới", new HashMap<>()),
    ACCOUNT_LINKED_USB(OK, "ACC-19", "Tài khoản đã được liên kết usb . Vui lòng gỡ usb cũ và thử lại", new HashMap<>()),
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
