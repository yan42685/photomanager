package com.example.photomanager.service;

import com.example.photomanager.bean.dto.ModifiableUserInfo;
import com.example.photomanager.bean.dto.RegistryInfo;
import com.example.photomanager.bean.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.photomanager.bean.vo.UserInfo;


public interface UserService extends IService<User> {
    /**
     * 修改用户信息
     */
    boolean modifyInfo(ModifiableUserInfo info);

    /**
     * 注册 返回的头像是预设的
     * 如果插入不成功，返回空的UserInfo
     * 插入成功，返回实际UserInfo
     *
     * @param info 传入的info
     * @return 返回UserInfo
     */
    UserInfo register(RegistryInfo info);

    /**
     * @param username 传入的username
     * @return 返回username是否已经存在
     */
    boolean checkUsername(String username);

    /**
     * @param email 传入的email
     * @return 返回email是否已经存在
     */
    boolean checkEmail(String email);

    /**
     * @param username   传入的用户名
     * @param password   传入的密码
     * @param rememberMe 是否勾选记住密码十天
     * @return 不抛异常，证明登录成功，直接返回true
     */
    boolean login(String username, String password, Boolean rememberMe);

    boolean getActiveCode(String email);

    boolean checkActiveCode(String email, String activeCode);

    boolean modifyPassword(String email, String password);
}
