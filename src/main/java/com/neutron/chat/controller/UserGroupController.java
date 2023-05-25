package com.neutron.chat.controller;

import com.neutron.chat.common.BaseResponse;
import com.neutron.chat.common.ErrorCode;
import com.neutron.chat.common.ResultUtils;
import com.neutron.chat.exception.BusinessException;
import com.neutron.chat.model.dto.UserDTO;
import com.neutron.chat.service.UserGroupService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zzs
 * @date 2023/5/25 16:45
 */
@RestController
@RequestMapping("/userGroup")
public class UserGroupController {

    @Resource
    private UserGroupService userGroupService;

    /**
     * 加入群聊接口
     *
     * @param groupId 群聊id
     * @param request servlet请求
     * @return 是否加入群聊成功
     */
    @PostMapping("/join")
    public BaseResponse<Boolean> joinGroup(Long groupId, HttpServletRequest request) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("user_login_state");

        if (groupId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long userId = loginUser.getId();
        Boolean joinGroup = userGroupService.joinGroup(userId, groupId);
        if (Boolean.FALSE.equals(joinGroup)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "加入群聊失败");
        }

        return ResultUtils.success(true, "加入群聊成功");
    }

}
