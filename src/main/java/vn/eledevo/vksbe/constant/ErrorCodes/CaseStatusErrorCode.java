package vn.eledevo.vksbe.constant.ErrorCodes;

import lombok.Getter;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum CaseStatusErrorCode implements BaseErrorCode {
    CASE_STATUS_NAME_ALREADY_EXIST(OK, "ST-01", "Tên trạng thái vụ án đã tồn tại", new HashMap<>()),
    CASE_STATUS_NOT_FOUND(OK, "ST-02", "Trạng thái vụ án không tồn tại", new HashMap<>()),
    CASE_STATUS_DEFAULT_EMPTY(OK, "ST-03", "Trạng thái vụ án mặc định chưa có trong hệ thống", new HashMap<>()),
    CASE_STATUS_IS_DEFAULT(OK, "ST-04", "Trạng thái vụ án này là mặc định", new HashMap<>()),
    CASE_STATUS_NOT_FOUND_IN_CASE(OK, "ST-10", "Không có vụ án nào chứa trạng thái vụ án này. Vui lòng kiểm tra lại", new HashMap<>());
    private final HttpStatusCode statusCode;
    private final String code;
    private final String message;
    private final Map<String, Optional<?>> result;

    CaseStatusErrorCode(HttpStatusCode statusCode, String code, String message, Map<String, Optional<?>> result) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
        this.result = result;
    }
    @Override
    public void setResult(Optional<?> value) {
        this.result.put(code, value);
    }
}