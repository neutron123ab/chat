package com.neutron.chat.service.impl;

import java.util.ArrayList;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neutron.chat.common.ErrorCode;
import com.neutron.chat.exception.BusinessException;
import com.neutron.chat.model.dto.UserDTO;
import com.neutron.chat.model.entity.Group;
import com.neutron.chat.model.entity.UserGroup;
import com.neutron.chat.service.GroupService;
import com.neutron.chat.mapper.GroupMapper;
import com.neutron.chat.service.UserGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zzs
 * @description 针对表【group(群组表)】的数据库操作Service实现
 * @createDate 2023-05-23 21:14:45
 */
@Slf4j
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group>
        implements GroupService {

    @Resource
    private UserGroupService userGroupService;

    @Override
    public List<Group> getGroupsUserJoin(HttpServletRequest request) {
        UserDTO loginUser = (UserDTO) request.getSession().getAttribute("user_login_state");
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        Long userId = loginUser.getId();
        //查询出用户加入的所有群聊id
        List<Long> groupIdList = userGroupService.query()
                .eq("user_id", userId)
                .list()
                .stream()
                .map(UserGroup::getGroupId)
                .collect(Collectors.toList());
        if (groupIdList.isEmpty()) {
            return new ArrayList<>();
        }
        //查询出所有群聊信息
        return listByIds(groupIdList);
    }

    @Override
    public List<Long> getGroupId(Long userId) {
        return userGroupService.query()
                .eq("user_id", userId)
                .list()
                .stream().map(UserGroup::getGroupId)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean createGroup(Long userId, String groupName) {
        if (userId == null || groupName == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Group group = new Group();
        group.setGroupOwnerId(userId);
        group.setGroupName(groupName);
        boolean save = save(group);
        if (!save) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "创建群聊失败");
        }
        //如果创建群聊成功，则将用户id与群聊id关联起来
        UserGroup userGroup = new UserGroup();
        userGroup.setUserId(userId);
        userGroup.setGroupId(group.getId());
        boolean userGroupSave = userGroupService.save(userGroup);
        if (!userGroupSave) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户与群聊关联失败");
        }
        return true;
    }
}




