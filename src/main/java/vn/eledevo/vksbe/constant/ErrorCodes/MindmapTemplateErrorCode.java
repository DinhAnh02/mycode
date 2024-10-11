package vn.eledevo.vksbe.constant.ErrorCodes;

import static org.springframework.http.HttpStatus.OK;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatusCode;

public enum MindmapTemplateErrorCode implements BaseErrorCode {
    MINDMAP_TEMPLATE_NOT_ENOUGH_PERMISSION(
            OK, "MMT-01", "Bạn không có quyền tạo sơ đồ mẫu trong phòng ban này", new HashMap<>()),
    MINDMAP_TEMPLATE_NAME_ALREADY_EXISTS(OK, "MMT-02", "Tên sơ đồ mẫu không được trùng", new HashMap<>()),
    MINDMAP_TEMPLATE_CREATE_FAILED(OK, "MMT-03", "Tạo mới sơ đồ mindmap thất bại", new HashMap<>()),
    MINDMAP_TEMPLATE_NAME_EMPTY(
            OK, "MMT-04", "Tên sơ đồ không được để trống, vui lòng lựa chọn phòng ban", new HashMap<>()),
    MINDMAP_TEMPLATE_NOT_FOUND(OK, "MMT-05", "Sơ đồ mẫu không tồn tại", new HashMap<>()),
    MINDMAP_TEMPLATE_NO_PERMISSION_TO_ACCESS(
            OK, "MMT-06", "Bạn không có quyền vào sơ đồ mẫu của phòng ban này", new HashMap<>()),
    MINDMAP_TEMPLATE_NAME_MINDMAP(OK, "MMT-07", "Tên sơ đồ đã tồn tại", new HashMap<>());


    private final HttpStatusCode statusCode;
    private final String code; // Đảm bảo `code` là String
    private final String message;
    private final Map<String, Optional<?>> result;

    MindmapTemplateErrorCode(HttpStatusCode statusCode, String code, String message, Map<String, Optional<?>> result) {
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
        if (value.isPresent()) {
            Object object = value.get();
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    String key = field.getName();
                    Object fieldValue = field.get(object);
                    this.result.put(key, Optional.ofNullable(fieldValue));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
