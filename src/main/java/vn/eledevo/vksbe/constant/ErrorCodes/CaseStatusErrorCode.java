package vn.eledevo.vksbe.constant.ErrorCodes;

import lombok.Getter;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum CaseStatusErrorCode implements BaseErrorCode {
    CASE_STATUS_NAME_ALREADY_EXIST(OK, "ST-01", "Tên trạng thái vụ án đã tồn tại", new HashMap<>());
    private final HttpStatusCode statusCode;
    private final String code; // Đảm bảo `code` là String
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