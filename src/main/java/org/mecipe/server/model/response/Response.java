package org.mecipe.server.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.mecipe.server.common.enums.ErrorCode;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class Response<T> implements Serializable {

    private int code;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T data;

    public static <T> Response<T> success(T data) {
        Response<T> resp = new Response<>();
        resp.setData(data);
        resp.setCode(HttpStatus.OK.value());
        return resp;
    }

    public static Response<Void> success() {
        Response<Void> resp = new Response<>();
        resp.setCode(HttpStatus.OK.value());
        return resp;
    }

    public static <T> Response<T> error(int errorCode, String errorMsg) {
        ErrorResponse<T> resp = new ErrorResponse<>();
        resp.setCode(errorCode);
        resp.setErrorMsg(errorMsg);
        return resp;
    }


    public static <T> ErrorResponse<T> error(ErrorCode errorCode) {
        ErrorResponse<T> resp = new ErrorResponse<T>();
        resp.setCode(errorCode.getCode());
        resp.setErrorMsg(errorCode.getMessage());
        return resp;
    }

    public static <T> ErrorResponse<T> error(ErrorCode errorCode, String errorMsg) {
        ErrorResponse<T> resp = new ErrorResponse<T>();
        resp.setCode(errorCode.getCode());
        resp.setErrorMsg(errorMsg);
        return resp;
    }

}