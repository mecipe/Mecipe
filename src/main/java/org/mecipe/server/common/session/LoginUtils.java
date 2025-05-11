package org.mecipe.server.common.session;

import cn.dev33.satoken.stp.StpUtil;
import org.mecipe.server.common.utils.BeanConverter;
import org.mecipe.server.model.response.LoginToken;

public class LoginUtils {

    public static void login(Long id) {
        StpUtil.login(id);
    }

    public static void logout() {
        StpUtil.logout();
    }

    public static boolean isLogin() {
        return StpUtil.isLogin();
    }

    public static void checkLogin() {
        StpUtil.checkLogin();
    }

    public static Long getLoginId() {
        if (StpUtil.getLoginIdDefaultNull() == null)
            return null;
        return StpUtil.getLoginIdAsLong();
    }

    public static Long getUserId() {
        return getLoginId();
    }

    public static LoginToken getToken() {
        return BeanConverter.toBean(StpUtil.getTokenInfo(), LoginToken.class);
    }

}
