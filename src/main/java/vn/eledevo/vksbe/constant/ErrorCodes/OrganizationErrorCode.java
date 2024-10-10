package vn.eledevo.vksbe.constant.ErrorCodes;

import org.springframework.http.HttpStatusCode;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

public enum OrganizationErrorCode implements BaseErrorCode{
    ORGANIZATION_NO_PERMISSION_TO_ACCESS(OK,"ORG-01","Bạn không có quyền thao tác", new HashMap<>()),
    ORGANIZATION_NAME_EXIST(OK,"ORG-02","Tên đơn vị đã tồn tại. Vui lòng nhập tên đơn vị khác", new HashMap<>()),
    ORGANIZATION_CODE_EXIST(OK,"ORG-03", "Tên đơn vị đã tồn tại. Vui lòng nhập tên đơn vị khác", new HashMap<>()),
    ORGANIZATION_NOT_FOUND(OK, "ORG-04","Đơn vị không tồn tại", new HashMap<>()),
    ORGANIZATION_DEFAULT(OK,"ORG-05","Đơn vị này là đơn vị mặc định và không được phép chỉnh sửa", new HashMap<>()),
    ORGANIZATION_CODE_EXISTED(OK, "ORG-06", "Mã code đã tồn tại.Vui lòng nhập mã đơn vị khác", new HashMap<>()),
    ORGANIZATION_SYSTEM_ERROR(OK, "ORG-07", "Lỗi hệ thống. Vui lòng thao tác lại", new HashMap<>()),
    ;

    private final HttpStatusCode statusCode;
    private final String code; // Đảm bảo `code` là String
    private final String message;
    private final Map<String, Optional<?>> result;

    OrganizationErrorCode(HttpStatusCode statusCode, String code, String message, Map<String, Optional<?>> result) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
        this.result = result;
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
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
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
