package com.neutron.chat.controller;

import cn.hutool.core.bean.BeanUtil;
import com.neutron.chat.common.BaseResponse;
import com.neutron.chat.common.ErrorCode;
import com.neutron.chat.common.ResultUtils;
import com.neutron.chat.exception.BusinessException;
import com.neutron.chat.model.dto.UserDTO;
import com.neutron.chat.model.request.UserLoginRequest;
import com.neutron.chat.model.request.UserRegisterRequest;
import com.neutron.chat.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zzs
 * @date 2023/5/23 21:36
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录接口
     *
     * @param userLoginRequest 登录请求
     * @return 登录用户信息
     */
    @PostMapping("/login")
    public BaseResponse<UserDTO> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (BeanUtil.isEmpty(userLoginRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserDTO userDTO = userService.userLogin(userLoginRequest);
        if (userDTO == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败");
        }
        request.getSession().setAttribute("user_login_state", userDTO);
        return ResultUtils.success(userDTO, "登录成功");
    }

    /**
     * 用户注册接口
     *
     * @param userRegisterRequest 注册请求
     * @return 是否注册成功
     */
    @PostMapping("/register")
    public BaseResponse<Boolean> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (BeanUtil.isEmpty(userRegisterRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean register = userService.userRegister(userRegisterRequest);

        return ResultUtils.success(register, "注册成功");
    }

}
