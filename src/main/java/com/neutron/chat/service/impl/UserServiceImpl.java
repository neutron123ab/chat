package com.neutron.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neutron.chat.model.entity.User;
import com.neutron.chat.service.UserService;
import com.neutron.chat.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author zzs
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-05-23 21:12:31
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




