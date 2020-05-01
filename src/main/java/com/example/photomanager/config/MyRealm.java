package com.example.photomanager.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.photomanager.bean.entity.User;
import com.example.photomanager.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 小朝
 * @date 2020/5/1
 **/
public class MyRealm extends AuthenticatingRealm {
    @Autowired
    private UserService userService;

    /**
     * 执行认证逻辑
     * @param token 用户输入的令牌，即用户名，密码
     * @return 返回信息
     * @throws AuthenticationException 异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken token1 = (UsernamePasswordToken) token;
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username",token1.getUsername());
        User user = userService.getOne(queryWrapper);
        if(user == null){
            // 用户名不存在
            return null;
        }
        return new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                // 用用户名当盐
                ByteSource.Util.bytes(user.getUsername()),
                this.getName());
    }
}
