package com.example.photomanager.service;

import com.example.photomanager.bean.dto.RegistryInfo;
import com.example.photomanager.bean.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.photomanager.bean.vo.UserInfo;
import com.example.photomanager.enums.UserInfoEnum;

public interface UserService extends IService<User> {
    /**
     * 修改用户信息
     */
    boolean modifyInfo(UserInfoEnum field, String data);

    /**
     * 注册, 返回的头像是默认图片
     */
    UserInfo register(RegistryInfo info);
}
