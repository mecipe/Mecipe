package org.mecipe.server.exception;

import cn.dev33.satoken.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.mecipe.server.common.enums.ErrorCode;
import org.mecipe.server.model.response.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理程序
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常处理
     */
    @ExceptionHandler(BusinessException.class)
    public Response businessExceptionHandler(BusinessException e) {
        log.error("businessException: {}", e.getErrorMessage(), e);
        return Response.error(e.getErrorCode(), e.getErrorMessage());
    }

    /**
     * 运行时异常处理程序
     */
    @ExceptionHandler(RuntimeException.class)
    public Response runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return Response.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }

    /**
     * Sa-Token未登录异常处理程序
     */
    @ExceptionHandler(NotLoginException.class)
    public Response notLoginExceptionHandler(NotLoginException e) {
        log.error("notLoginException", e);
        return Response.error(ErrorCode.NOT_LOGIN);
    }

}
