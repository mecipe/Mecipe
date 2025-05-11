package org.mecipe.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import jakarta.annotation.Resource;
import org.mecipe.server.model.request.user.dto.UserDTO;
import org.mecipe.server.model.request.user.dto.UserLoginDTO;
import org.mecipe.server.model.request.user.dto.UserLogoutDTO;
import org.mecipe.server.model.request.user.dto.UserRegisterDTO;
import org.mecipe.server.model.response.Response;
import org.mecipe.server.model.response.user.vo.UserVO;
import org.mecipe.server.service.IUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService iUserService;

    @PostMapping("/register")
    Response<Boolean> register(@RequestBody UserRegisterDTO registerDTO) {
        return Response.success(iUserService.register(registerDTO));
    }

    @PostMapping("/login")
    Response<UserVO> login(@RequestBody UserLoginDTO loginDTO) {
        return Response.success(iUserService.login(loginDTO));
    }

    @SaCheckLogin
    @PostMapping("/update")
    Response<UserVO> update(@RequestBody UserDTO userDTO) {
        return Response.success(iUserService.update(userDTO));
    }

    @SaCheckLogin
    @PostMapping("/logout")
    Response<Boolean> logout(@RequestBody UserLogoutDTO logoutDTO) {
        return Response.success(iUserService.logout(logoutDTO));
    }

}
