package com.example.photomanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.photomanager.bean.dto.RegistryInfo;
import com.example.photomanager.bean.entity.User;
import com.example.photomanager.bean.vo.UserInfo;
import com.example.photomanager.enums.UserInfoEnum;
import com.example.photomanager.mapper.UserMapper;
import com.example.photomanager.service.QZ_MailService;
import com.example.photomanager.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private QZ_MailService mailService;

    /**
     * 验证码的位数
     */
    private final static int CODE_COUNT = 6;

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
        // 进行Sha256加密,用户名作为盐，并转为16进制
        Sha256Hash sha256Hash = new Sha256Hash(info.getPassword(),info.getUsername(),1024);
        info.setPassword(sha256Hash.toHex());
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
     * @param username 传入的username
     * @return 返回username是否已经存在
     */
    @Override
    public boolean checkUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User user = getOne(wrapper);
        return user!=null;
    }

    /**
     *
     * @param email 传入的email
     * @return 返回email是否已经存在
     */
    @Override
    public boolean checkEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        User user = getOne(wrapper);
        return user!=null;
    }

    /**
     * @param username 传入的用户名
     * @param password 传入的密码
     * @param rememberMe 是否勾选记住密码十天
     * @return 不抛异常，证明登录成功，直接返回true
     */
    @Override
    public boolean login(String username, String password, Boolean rememberMe) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        subject.login(token);
        if(rememberMe){
            // 勾选了记住密码，则记住密码10天
            token.setRememberMe(true);
        }else {
            token.setRememberMe(false);
        }
        return true;
    }

    @Override
    public boolean getActiveCode(String email, HttpSession session) {
        Random random = new Random();
        StringBuffer result= new StringBuffer();
        for (int i=0;i<CODE_COUNT;i++){
            result.append(random.nextInt(10));
        }
        // session存入验证码，以email作为name，验证码作为value
        session.setAttribute(email,result.toString());
        mailService.sendSimpleMail(email,result.toString());
        return true;
    }

    @Override
    public boolean checkActiveCode(String email, String activeCode, HttpSession session) {
        // 通过email来从session中获取验证码，再和用户输入的验证码进行比对
        String active = (String) session.getAttribute(email);
        return active.equals(activeCode);
    }

    @Override
    public boolean modifyPassword(String email, String password) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password",password).eq("email",email);
        return this.update(updateWrapper);
    }
}
