package com.neutron.chat.controller;

import com.neutron.chat.common.BaseResponse;
import com.neutron.chat.common.ErrorCode;
import com.neutron.chat.common.ResultUtils;
import com.neutron.chat.exception.BusinessException;
import com.neutron.chat.model.dto.UserDTO;
import com.neutron.chat.model.entity.Group;
import com.neutron.chat.service.GroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zzs
 * @date 2023/5/24 20:48
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    @Resource
    private GroupService groupService;

    /**
     * 查询用户加入的所有群聊
     * @param request servlet 请求
     * @return 群聊列表
     */
    @GetMapping("/list")
    public BaseResponse<List<Group>> getGroupsUserJoin(HttpServletRequest request) {
        List<Group> groupList = groupService.getGroupsUserJoin(request);
        return ResultUtils.success(groupList, "获取用户加入的群聊信息成功");
    }

    /**
     * 创建群聊接口
     * @param groupName 群聊名称
     * @param request servlet请求
     * @return 是否创建成功
     */
    @PostMapping("/create")
    public BaseResponse<Boolean> createGroup(String groupName, HttpServletRequest request) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("user_login_state");
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        Boolean group = groupService.createGroup(loginUser.getId(), groupName);
        if (Boolean.FALSE.equals(group)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "创建群聊失败");
        }
        return ResultUtils.success(true, "创建群聊成功");
    }
}
