package vn.eledevo.vksbe.constant.ErrorCodes;

import org.springframework.http.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

public enum OrganizationErrorCode implements BaseErrorCode{
    ORGANIZATION_NO_PERMISSION_TO_ACCESS(OK, "ORG-01", "Bạn không có quyền thao tác", new HashMap<>()),
    ORGANIZATION_NAME_EXIST(OK, "ORG-02", "Tên đơn vị đã tồn tại. Vui lòng nhập tên đơn vị khác", new HashMap<>()),
    ORGANIZATION_CODE_EXIST(OK, "ORG-03", "Mã đơn vị đã tồn tại. Vui lòng nhập tên đơn vị khác", new HashMap<>()),
    ORGANIZATION_NOT_FOUND(OK, "ORG-04", "Đơn vị không tồn tại", new HashMap<>()),
    ORGANIZATION_DEFAULT(OK, "ORG-05", "Đơn vị này là đơn vị mặc định và không được phép chỉnh sửa", new HashMap<>()),
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
    public void setResult(Optional<?> value) {}
}
