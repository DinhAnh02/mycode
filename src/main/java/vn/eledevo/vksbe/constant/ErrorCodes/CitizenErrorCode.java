package vn.eledevo.vksbe.constant.ErrorCodes;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import java.util.Map;
import java.util.Optional;

@Getter
public enum CitizenErrorCode implements BaseErrorCode {
    ;

    private final HttpStatusCode statusCode;
    private final String code;
    private final String message;
    private final Map<String, Optional<?>> result;

    CitizenErrorCode(HttpStatusCode statusCode, String code, String message, Map<String, Optional<?>> result) {
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
