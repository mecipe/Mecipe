package org.mecipe.server.common.enums;

import lombok.Getter;

/**
 * 登录方式
 */
@Getter
public enum LoginType {

    USERNAME_PASSWORD(100, "账号密码登录"),

    PHONE(200, "手机号登录"),

    EMAIL(300, "邮箱登录");

//    SOCIAL_MEDIA(400, "社交媒体登录");

    private final int code;

    private final String description;

    LoginType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static LoginType getLoginType(int code) {
        for (LoginType loginType : values()) {
            if (loginType.getCode() == code) {
                return loginType;
            }
        }
        return null;
    }

}
