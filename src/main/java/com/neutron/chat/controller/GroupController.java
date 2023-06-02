package com.neutron.chat.controller;

import cn.hutool.core.bean.BeanUtil;
import com.neutron.chat.common.BaseResponse;
import com.neutron.chat.common.ErrorCode;
import com.neutron.chat.common.ResultUtils;
import com.neutron.chat.exception.BusinessException;
import com.neutron.chat.model.dto.UserDTO;
import com.neutron.chat.model.entity.Group;
import com.neutron.chat.model.request.CreateGroupRequest;
import com.neutron.chat.service.GroupService;
import org.springframework.web.bind.annotation.*;

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
     * @param createGroupRequest 创建群聊请求
     * @param request servlet请求
     * @return 是否创建成功
     */
    @PostMapping("/create")
    public BaseResponse<Boolean> createGroup(@RequestBody CreateGroupRequest createGroupRequest, HttpServletRequest request) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("user_login_state");
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        if (BeanUtil.hasNullField(createGroupRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean group = groupService.createGroup(loginUser.getId(), createGroupRequest.getGroupName());
        if (Boolean.FALSE.equals(group)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "创建群聊失败");
        }
        return ResultUtils.success(true, "创建群聊成功");
    }

    /**
     * 列出所有群聊
     * @return 群聊列表
     */
    @GetMapping("/listAll")
    public BaseResponse<List<Group>> listAllGroup() {
        List<Group> list = groupService.list();
        return ResultUtils.success(list);
    }
}
