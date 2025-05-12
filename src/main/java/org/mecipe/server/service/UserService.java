package org.mecipe.server.service;

import org.mecipe.server.model.entity.UserEntity;
import org.mecipe.server.model.request.user.UserDTO;
import org.mecipe.server.model.request.user.UserLoginDTO;
import org.mecipe.server.model.request.user.UserLogoutDTO;
import org.mecipe.server.model.request.user.UserRegisterDTO;
import org.mecipe.server.model.response.user.UserVO;

public interface UserService {

    boolean register(UserRegisterDTO registerDTO);

    UserVO login(UserLoginDTO loginDTO);

    UserVO update(UserDTO updateDTO);

    boolean logout(UserLogoutDTO logoutDTO);

    void checkUserNotBlocked(UserEntity userEntity);

}
