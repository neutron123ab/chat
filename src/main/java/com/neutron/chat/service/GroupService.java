package com.neutron.chat.service;

import com.neutron.chat.model.entity.Group;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author zzs
* @description 针对表【group(群组表)】的数据库操作Service
* @createDate 2023-05-23 21:14:45
*/
public interface GroupService extends IService<Group> {

    /**
     * 查询用户加入的所有群聊
     *
     * @param request servlet 请求
     * @return 群聊列表
     */
    List<Group> getGroupsUserJoin(HttpServletRequest request);

    /**
     * 创建群聊
     *
     * @param userId 群主id
     * @param groupName 群聊名称
     * @return 是否创建成功
     */
    Boolean createGroup(Long userId, String groupName);

}
