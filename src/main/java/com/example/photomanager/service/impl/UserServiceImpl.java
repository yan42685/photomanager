package com.example.photomanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.example.photomanager.bean.dto.RegistryInfo;
import com.example.photomanager.bean.entity.User;
import com.example.photomanager.bean.vo.UserInfo;
import com.example.photomanager.enums.ExceptionEnum;
import com.example.photomanager.enums.UserInfoEnum;
import com.example.photomanager.mapper.UserMapper;
import com.example.photomanager.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public boolean modifyInfo(UserInfoEnum field, String data) {
        // TODO: 如果用户登录了 那么应该有一个util方法获取当前用户 id
        return true;
    }

    /**
     * 邱朝
     * 如果插入不成功，返回空的UserInfo
     * 插入成功，返回实际UserInfo
     * @param info 传入的info
     * @return 返回UserInfo
     */
    @Override
    public UserInfo register(RegistryInfo info) {
        User user = new User();
        BeanUtils.copyProperties(info,user);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setAvatarUrl(null);
        boolean result = save(user);
        if(!result){
            return new UserInfo();
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user,userInfo);
        return userInfo;
    }

    /**
     * user!= null 证明重复，返回false
     * user== null  证明不重复，返回true
     * @param username 传入的username
     * @return
     */
    @Override
    public boolean checkUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User user = getOne(wrapper);
        return user==null;
    }

    /**
     * user!= null 证明重复，返回false
     * user== null  证明不重复，返回true
     * @param email 传入的email
     * @return
     */
    @Override
    public boolean checkEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        User user = getOne(wrapper);
        return user==null;
    }
}
