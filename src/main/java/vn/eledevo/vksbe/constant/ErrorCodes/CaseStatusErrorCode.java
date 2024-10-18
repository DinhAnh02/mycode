package vn.eledevo.vksbe.constant.ErrorCodes;

import static org.springframework.http.HttpStatus.OK;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum CaseStatusErrorCode implements BaseErrorCode {
    CASE_STATUS_NAME_ALREADY_EXIST(OK, "ST-01", "Tên trạng thái vụ án đã tồn tại", new HashMap<>()),
    CASE_STATUS_NOT_FOUND(OK, "ST-02", "Trạng thái vụ án không tồn tại", new HashMap<>()),
    CASE_STATUS_DEFAULT_EMPTY(OK, "ST-03", "Trạng thái vụ án mặc định chưa có trong hệ thống", new HashMap<>()),
    CASE_STATUS_IS_DEFAULT(OK, "ST-04", "Trạng thái vụ án này là mặc định", new HashMap<>());
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
