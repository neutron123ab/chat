package com.neutron.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neutron.chat.model.entity.UserGroup;
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

}




