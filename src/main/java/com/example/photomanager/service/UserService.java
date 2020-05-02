package com.example.photomanager.service;

import com.example.photomanager.bean.dto.RegistryInfo;
import com.example.photomanager.bean.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.photomanager.bean.vo.UserInfo;
import com.example.photomanager.enums.UserInfoEnum;

import javax.servlet.http.HttpSession;

public interface UserService extends IService<User> {
    /**
     * 修改用户信息
     */
    boolean modifyInfo(UserInfoEnum field, String data);

    UserInfo register(RegistryInfo info);

    boolean checkUsername(String username);

    boolean checkEmail(String email);

    boolean login(String username, String password, Boolean rememberMe);

    boolean getActiveCode(String email, HttpSession session);

    boolean checkActiveCode(String email, String activeCode, HttpSession session);

    boolean modifyPassword(String email, String password);
}
