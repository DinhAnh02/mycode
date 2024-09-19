package vn.eledevo.vksbe.exception;

import lombok.Getter;
import vn.eledevo.vksbe.constant.ErrorCode;

@Getter
public class ApiException extends Exception {
    private final ErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
