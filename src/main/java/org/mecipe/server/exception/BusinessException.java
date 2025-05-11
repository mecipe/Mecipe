package org.mecipe.server.exception;

import lombok.Getter;
import org.mecipe.server.common.enums.ErrorCode;

/**
 * 业务异常
 */
@Getter
public class BusinessException extends RuntimeException {

    private final int errorCode;

    private final String errorMessage;

    public BusinessException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorCode.getMessage();
    }

    public BusinessException(ErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorMessage;
    }

}