package vn.eledevo.vksbe.constant.ErrorCodes;

import static org.springframework.http.HttpStatus.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatusCode;

public enum SystemErrorCode implements BaseErrorCode {
    INTERNAL_SERVER(INTERNAL_SERVER_ERROR, "500", "Internal Server Error", new HashMap<>()),
    UNAUTHORIZED_SERVER(UNAUTHORIZED, "401", "Unauthorized", new HashMap<>()),
    AUTHENTICATION_SERVER(FORBIDDEN, "403", "Forbidden", new HashMap<>()),
    NOT_FOUND_SERVER(NOT_FOUND, "404", "Not Found", new HashMap<>()),
    BAD_REQUEST_SERVER(BAD_REQUEST, "400", "Bad Request", new HashMap<>()),
    VALIDATE_FORM(UNPROCESSABLE_ENTITY, "422", "Dữ liệu không hợp lệ", new HashMap<>()),
    ORGANIZATION_STRUCTURE(
            OK, "1000", "Cơ cấu tổ chức đã thay đổi. Vui lòng đăng nhập lại để có dữ liệu mới nhất", new HashMap<>()),
    ;

    private final HttpStatusCode statusCode;
    private final String code; // Đảm bảo `code` là String
    private final String message;
    private final Map<String, Optional<?>> result; // Đảm bảo `result` là Map<String, String>

    SystemErrorCode(HttpStatusCode statusCode, String code, String message, Map<String, Optional<?>> result) {
        this.statusCode = statusCode;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    @Override
    public String getCode() { // Trả về kiểu String
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setResult(Optional<?> value) {
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

    @Override
    public Map<String, Optional<?>> getResult() { // Đảm bảo `result` là Map<String, String>
        return result;
    }
}
