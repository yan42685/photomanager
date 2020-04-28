package com.example.photomanager.service.impl;

import com.example.photomanager.bean.entity.User;
import com.example.photomanager.enums.UserInfoEnum;
import com.example.photomanager.mapper.UserMapper;
import com.example.photomanager.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public boolean modifyInfo(UserInfoEnum field) {
        // TODO: 如果用户登录了 那么应该有一个util方法获取当前用户 id
        return true;
    }
}
