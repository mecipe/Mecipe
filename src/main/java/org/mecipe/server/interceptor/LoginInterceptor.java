package org.mecipe.server.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.mecipe.server.common.session.LoginUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            String uri = request.getRequestURI();

            log.info("access URI :{}, isLogin: [{}], login id: {}", uri, LoginUtils.isLogin(), LoginUtils.getLoginId());
            return true;
        } catch (Exception ex) {
            log.error("login interceptor exception ", ex);
            return true;
        }
    }

}