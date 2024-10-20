package vn.eledevo.vksbe.constant.ErrorCodes;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@Getter
public enum CaseFlowErrorCode implements BaseErrorCode{
    CASE_FLOW_NOT_ACCESS(FORBIDDEN, "403","Bạn không được quyền thao tác vụ án này", new HashMap<>()),
    CASE_FLOW_NOT_FOUND(OK, "CF-01","Sơ đồ vụ án không tồn tại", new HashMap<>()),
    CASE_FLOW_IS_PRESENTED(OK, "CF-02","Vụ án đã có sơ đồ", new HashMap<>()),
    CASE_FLOW_NOT_MATCH_CASE(OK, "CF-03","Sơ đồ vụ án không khớp với vụ án", new HashMap<>()),
    CASE_NOT_MATCH_DEPARTMENT(OK, "CF-04","Vụ án không khớp với phòng ban", new HashMap<>());
    private final HttpStatusCode statusCode;
    private final String code;
    private final String message;
    private final Map<String, Optional<?>> result;

    CaseFlowErrorCode(HttpStatusCode statusCode, String code, String message, Map<String, Optional<?>> result) {
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