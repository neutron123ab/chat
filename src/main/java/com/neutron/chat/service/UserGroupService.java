package com.neutron.chat.service;

import com.neutron.chat.model.entity.UserGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neutron.chat.model.request.JoinGroupRequest;

/**
* @author zzs
* @description 针对表【user_group(用户-群组关联表)】的数据库操作Service
* @createDate 2023-05-23 21:15:35
*/
public interface UserGroupService extends IService<UserGroup> {

    /**
     * 加入群聊
     *
     * @param userId 用户id
     * @param groupId 群聊id
     * @return 是否加入成功
     */
    Boolean joinGroup(Long userId, Long groupId);
}
