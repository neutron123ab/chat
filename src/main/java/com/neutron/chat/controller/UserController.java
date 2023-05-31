package com.neutron.chat.controller;

import cn.hutool.core.bean.BeanUtil;
import com.neutron.chat.common.BaseResponse;
import com.neutron.chat.common.ErrorCode;
import com.neutron.chat.common.ResultUtils;
import com.neutron.chat.exception.BusinessException;
import com.neutron.chat.model.dto.UserDTO;
import com.neutron.chat.model.entity.User;
import com.neutron.chat.model.request.UserLoginRequest;
import com.neutron.chat.model.request.UserRegisterRequest;
import com.neutron.chat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zzs
 * @date 2023/5/23 21:36
 */
@Slf4j
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

    /**
     * 退出登录接口
     *
     * @param request 请求
     * @return 是否退出登录成功
     */
    @DeleteMapping("/logout")
    public BaseResponse<Boolean> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user_login_state");
        return ResultUtils.success(true, "用户退出登录成功");
    }

    /**
     * 获取用户列表
     *
     * @param request servlet请求
     * @return 除了自身的用户列表信息
     */
    @GetMapping("/getAllUsers")
    public BaseResponse<List<UserDTO>> getAllUsers(HttpServletRequest request) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("user_login_state");
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        List<UserDTO> userDTOList = userService.query()
                .ne("id", loginUser.getId())
                .list().stream()
                .map(user -> BeanUtil.copyProperties(user, UserDTO.class))
                .collect(Collectors.toList());
        return ResultUtils.success(userDTOList, "获取用户列表成功");
    }

}
