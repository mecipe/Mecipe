package org.mecipe.server.service;

import org.mecipe.server.model.entity.UserEntity;
import org.mecipe.server.model.request.user.dto.UserDTO;
import org.mecipe.server.model.request.user.dto.UserLoginDTO;
import org.mecipe.server.model.request.user.dto.UserLogoutDTO;
import org.mecipe.server.model.request.user.dto.UserRegisterDTO;
import org.mecipe.server.model.response.user.vo.UserVO;

public interface IUserService {

    boolean register(UserRegisterDTO registerDTO);

    UserVO login(UserLoginDTO loginDTO);

    UserVO update(UserDTO updateDTO);

    boolean logout(UserLogoutDTO logoutDTO);

    void checkUserNotBlocked(UserEntity userEntity);

}
