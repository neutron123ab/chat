package com.neutron.chat.service.impl;
import java.util.Date;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neutron.chat.common.ErrorCode;
import com.neutron.chat.exception.BusinessException;
import com.neutron.chat.model.entity.UserGroup;
import com.neutron.chat.model.request.JoinGroupRequest;
import com.neutron.chat.service.UserGroupService;
import com.neutron.chat.mapper.UserGroupMapper;
import org.springframework.stereotype.Service;

/**
* @author zzs
* @description 针对表【user_group(用户-群组关联表)】的数据库操作Service实现
* @createDate 2023-05-23 21:15:35
*/
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup>
    implements UserGroupService{

    @Override
    public Boolean joinGroup(Long userId, Long groupId) {

        UserGroup userGroup = new UserGroup();
        userGroup.setUserId(userId);
        userGroup.setGroupId(groupId);
        boolean save = save(userGroup);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "用户加入群聊失败");
        }
        return true;
    }
}




