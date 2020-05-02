package com.example.photomanager.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.photomanager.bean.entity.User;
import com.example.photomanager.common.KnownException;
import com.example.photomanager.enums.ExceptionEnum;
import com.example.photomanager.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 获取当前登录用户的id
 * @author 小朝
 * @date 2020/5/2
 **/
public class QZ_IdUtils {
    private static UserService userService = SpringContextUtils.getBean("userServiceImpl",UserService.class);

    public static Long getUserId(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()){
            // 用户已登录或者记住密码，才可获取用户名
            String username = (String) subject.getPrincipal();
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",username);
            User user = userService.getOne(queryWrapper);
            return user.getId();
        }else {
            // 未登录或者未记住密码，抛出未登录异常
            throw new KnownException(ExceptionEnum.NOT_LOGIN);
        }
    }
}
