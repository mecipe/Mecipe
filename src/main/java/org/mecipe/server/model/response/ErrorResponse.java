package org.mecipe.server.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mecipe.server.common.enums.ErrorCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ErrorResponse<T> extends Response<T> {

    private String errorMsg;

    public ErrorResponse() {
    }

    public ErrorResponse(ErrorCode errorCode) {
        this.setCode(errorCode.getCode());
        this.setErrorMsg(errorCode.getMessage());
    }

    public ErrorResponse(int code, String errorMsg) {
        this.setCode(code);
        this.setErrorMsg(errorMsg);
    }

}