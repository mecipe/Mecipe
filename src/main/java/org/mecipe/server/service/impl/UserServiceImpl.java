package org.mecipe.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mecipe.server.common.utils.BeanConverter;
import org.mecipe.server.common.utils.Encrypter;
import org.mecipe.server.common.utils.RandomGenerator;
import org.mecipe.server.common.utils.Valider;
import org.mecipe.server.common.enums.ErrorCode;
import org.mecipe.server.common.enums.LoginType;
import org.mecipe.server.common.session.LoginUtils;
import org.mecipe.server.exception.BusinessException;
import org.mecipe.server.mapper.UserMapper;
import org.mecipe.server.model.entity.UserEntity;
import org.mecipe.server.model.request.user.dto.UserDTO;
import org.mecipe.server.model.request.user.dto.UserLoginDTO;
import org.mecipe.server.model.request.user.dto.UserLogoutDTO;
import org.mecipe.server.model.request.user.dto.UserRegisterDTO;
import org.mecipe.server.model.response.LoginToken;
import org.mecipe.server.model.response.user.vo.UserVO;
import org.mecipe.server.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

    private final ReentrantLock lock = new ReentrantLock();

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean register(UserRegisterDTO registerDTO) {
        Valider.validateNullParams(registerDTO);
        // 不允许同时为空
        if (StringUtils.isAllBlank(registerDTO.getUsername(), registerDTO.getEmail(), registerDTO.getPhoneNumber())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不允许为空");
        }
        UserEntity userEntity = BeanConverter.toBean(registerDTO, UserEntity.class);
        if (StringUtils.isBlank(userEntity.getNickName())) {
            userEntity.setNickName(RandomGenerator.generateNickName());
        }
        userEntity.setPassword(Encrypter.encryptPassword(registerDTO.getPassword()));
        lock.lock();
        try {
            userMapper.insert(userEntity);
            return true;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.USERNAME_EXIST);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public UserVO login(UserLoginDTO loginDTO) {
        Valider.validateNullParams(loginDTO);
        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery(UserEntity.class);
        LoginType loginType = LoginType.getLoginType(loginDTO.getLoginTypeCode());
        switch (loginType) {
            case USERNAME_PASSWORD -> queryWrapper.eq(UserEntity::getUsername, loginDTO.getUsername());
            case PHONE -> queryWrapper.eq(UserEntity::getPhoneNumber, loginDTO.getPhoneNumber());
            case EMAIL -> queryWrapper.eq(UserEntity::getEmail, loginDTO.getEmail());
            case null, default -> throw new BusinessException(ErrorCode.PARAMS_ERROR, "不被支持的登录方式");
        }
        queryWrapper.eq(UserEntity::getPassword, Encrypter.encryptPassword(loginDTO.getPassword()));
        UserEntity userEntity = userMapper.selectOne(queryWrapper);
        if (userEntity == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码错误");
        }
        // 检查是否被封禁
        checkUserNotBlocked(userEntity);
        LoginUtils.login(userEntity.getId());
        UserVO userVO = BeanConverter.toBean(userEntity, UserVO.class);
        // 登录成功后，生成token
        LoginToken loginToken = LoginUtils.getToken();
        userVO.setLoginToken(loginToken);
        return userVO;
    }

    @Override
    public UserVO update(UserDTO updateDTO) {
        Valider.validateNullParams(updateDTO);
        UserEntity userEntity = BeanConverter.toBean(updateDTO, UserEntity.class);
        userEntity.setId(LoginUtils.getUserId());
        userMapper.updateById(userEntity);
        return BeanConverter.toBean(userEntity, UserVO.class);
    }

    @Override
    public boolean logout(UserLogoutDTO logoutDTO) {
        LoginUtils.logout();
        return true;
    }

    @Override
    public void checkUserNotBlocked(UserEntity userEntity) {
        if (userEntity != null && userEntity.getStatus() == 0) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "用户已被封禁");
        }
    }

}




