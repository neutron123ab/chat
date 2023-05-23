package com.neutron.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neutron.chat.model.entity.Group;
import com.neutron.chat.service.GroupService;
import com.neutron.chat.mapper.GroupMapper;
import org.springframework.stereotype.Service;

/**
* @author zzs
* @description 针对表【group(群组表)】的数据库操作Service实现
* @createDate 2023-05-23 21:14:45
*/
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group>
    implements GroupService{

}




