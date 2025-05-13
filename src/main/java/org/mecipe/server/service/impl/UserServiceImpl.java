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
import org.mecipe.server.model.request.user.UserDTO;
import org.mecipe.server.model.request.user.UserLoginDTO;
import org.mecipe.server.model.request.user.UserLogoutDTO;
import org.mecipe.server.model.request.user.UserRegisterDTO;
import org.mecipe.server.model.response.LoginToken;
import org.mecipe.server.model.response.user.UserVO;
import org.mecipe.server.service.UserService;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    private final ReentrantLock lock = new ReentrantLock();

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean register(UserRegisterDTO registerParam) {
        Valider.validateNullParams(registerParam);
        // 不允许同时为空
        if (StringUtils.isAllBlank(registerParam.getUsername(), registerParam.getEmail(), registerParam.getPhoneNumber())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不允许为空");
        }
        UserEntity userEntity = BeanConverter.toBean(registerParam, UserEntity.class);
        if (StringUtils.isBlank(userEntity.getNickName())) {
            userEntity.setNickName(RandomGenerator.generateNickName());
        }
        userEntity.setPassword(Encrypter.encryptPassword(registerParam.getPassword()));
        lock.lock();
        try {
            userMapper.insert(userEntity);
            return true;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名已存在");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public UserVO login(UserLoginDTO loginParam) {
        Valider.validateNullParams(loginParam);
        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.lambdaQuery(UserEntity.class);
        LoginType loginType = LoginType.getLoginType(loginParam.getLoginTypeCode());
        switch (loginType) {
            case USERNAME_PASSWORD -> queryWrapper.eq(UserEntity::getUsername, loginParam.getUsername());
            case PHONE -> queryWrapper.eq(UserEntity::getPhoneNumber, loginParam.getPhoneNumber());
            case EMAIL -> queryWrapper.eq(UserEntity::getEmail, loginParam.getEmail());
            case null, default -> throw new BusinessException(ErrorCode.PARAMS_ERROR, "不被支持的登录方式");
        }
        queryWrapper.eq(UserEntity::getPassword, Encrypter.encryptPassword(loginParam.getPassword()));
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
    public UserVO update(UserDTO updateParam) {
        Valider.validateNullParams(updateParam);
        UserEntity userEntity = BeanConverter.toBean(updateParam, UserEntity.class);
        userEntity.setId(LoginUtils.getUserId());
        userMapper.updateById(userEntity);
        return BeanConverter.toBean(userEntity, UserVO.class);
    }

    @Override
    public boolean logout(UserLogoutDTO logoutParam) {
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




