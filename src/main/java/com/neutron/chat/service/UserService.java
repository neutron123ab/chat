package com.neutron.chat.service;

import com.neutron.chat.model.dto.UserDTO;
import com.neutron.chat.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neutron.chat.model.request.UserLoginRequest;
import com.neutron.chat.model.request.UserRegisterRequest;

/**
* @author zzs
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-05-23 21:12:31
*/
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param userLoginRequest 登录请求
     * @return 登录用户信息
     */
    UserDTO userLogin(UserLoginRequest userLoginRequest);

    /**
     * 用户注册
     * @param userRegisterRequest 注册请求
     * @return 是否注册成功
     */
    Boolean userRegister(UserRegisterRequest userRegisterRequest);

}
