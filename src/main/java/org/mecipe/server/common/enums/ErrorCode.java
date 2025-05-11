package org.mecipe.server.common.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 成功
    SUCCESS(0, "ok"),

    // 用户名已存在
    USERNAME_EXIST(10001, "用户名已存在"),

    // 参数错误
    PARAMS_ERROR(40000, "请求参数错误"),

    // 请求数据为空
    NULL_ERROR(40001, "请求数据为空"),

    // 未登录
    NOT_LOGIN(40100, "未登录"),

    // 密码错误
    PASSWORD_ERROR(40100, "密码错误"),

    // 无权限
    NO_AUTH(40101, "无权限"),

    // 禁止操作
    FORBIDDEN(40301, "禁止操作"),

    // 系统内部异常
    SYSTEM_ERROR(50000, "系统内部异常");

    private final int code;

    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}