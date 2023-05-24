package com.neutron.chat.service.impl;
import java.util.Date;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neutron.chat.common.ErrorCode;
import com.neutron.chat.exception.BusinessException;
import com.neutron.chat.model.dto.UserDTO;
import com.neutron.chat.model.entity.User;
import com.neutron.chat.model.request.UserLoginRequest;
import com.neutron.chat.model.request.UserRegisterRequest;
import com.neutron.chat.service.UserService;
import com.neutron.chat.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author zzs
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-05-23 21:12:31
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public UserDTO userLogin(UserLoginRequest userLoginRequest) {
        if (BeanUtil.hasNullField(userLoginRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String username = userLoginRequest.getUsername();
        String password = userLoginRequest.getPassword();
        User user = query().eq("username", username).eq("password", password).one();
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码错误");
        }
        return BeanUtil.copyProperties(user, UserDTO.class);
    }

    @Override
    public Boolean userRegister(UserRegisterRequest userRegisterRequest) {
        if (BeanUtil.hasNullField(userRegisterRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不一致");
        }
        User user = new User();
        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(password);
        boolean save = save(user);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "向数据库插入用户失败");
        }

        return true;
    }
}




