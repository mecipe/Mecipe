package org.mecipe.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import jakarta.annotation.Resource;
import org.mecipe.server.model.request.user.UserDTO;
import org.mecipe.server.model.request.user.UserLoginDTO;
import org.mecipe.server.model.request.user.UserLogoutDTO;
import org.mecipe.server.model.request.user.UserRegisterDTO;
import org.mecipe.server.model.response.Response;
import org.mecipe.server.model.response.user.UserVO;
import org.mecipe.server.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    Response<Boolean> register(@RequestBody UserRegisterDTO registerParam) {
        return Response.success(userService.register(registerParam));
    }

    @PostMapping("/login")
    Response<UserVO> login(@RequestBody UserLoginDTO loginParam) {
        return Response.success(userService.login(loginParam));
    }

    @SaCheckLogin
    @PostMapping("/update")
    Response<UserVO> update(@RequestBody UserDTO userParam) {
        return Response.success(userService.update(userParam));
    }

    @SaCheckLogin
    @PostMapping("/logout")
    Response<Boolean> logout(@RequestBody UserLogoutDTO logoutParam) {
        return Response.success(userService.logout(logoutParam));
    }

}
