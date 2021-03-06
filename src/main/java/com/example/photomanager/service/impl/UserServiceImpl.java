package com.example.photomanager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photomanager.bean.dto.ModifiableUserInfo;
import com.example.photomanager.bean.dto.RegistryInfo;
import com.example.photomanager.bean.entity.User;
import com.example.photomanager.bean.vo.UserInfo;
import com.example.photomanager.mapper.UserMapper;
import com.example.photomanager.service.AlbumService;
import com.example.photomanager.service.QZ_MailService;
import com.example.photomanager.service.UserService;
import com.example.photomanager.util.QZ_IdUtils;
import com.example.photomanager.util.ServletUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Random;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private QZ_MailService mailService;
    @Autowired
    private AlbumService albumService;

    /**
     * 验证码的位数
     */
    private final static int CODE_COUNT = 6;

    @Override
    public UserInfo getInfo() {
        Long id = QZ_IdUtils.getUserId();
        User currentUser = getById(id);
        UserInfo info = new UserInfo();
        BeanUtil.copyProperties(currentUser, info);
        info.setAlbumCount(albumService.getCurrentAlbum().size());
        return info;
    }

    @Override
    public UserInfo modifyInfo(ModifiableUserInfo info) {
        Long id = QZ_IdUtils.getUserId();
        User currentUser = getById(id);
        BeanUtil.copyProperties(info, currentUser, CopyOptions.create().setIgnoreNullValue(true));
        updateById(currentUser);
        return getInfo();
    }

    @Override
    public UserInfo register(RegistryInfo info) {
        // 进行Sha256加密,用户名作为盐，并转为16进制
        Sha256Hash sha256Hash = new Sha256Hash(info.getPassword(), info.getUsername(), 1024);
        info.setPassword(sha256Hash.toHex());
        User user = new User();
        BeanUtils.copyProperties(info, user);
        boolean result = save(user);
        if (!result) {
            return new UserInfo();
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        return userInfo;
    }

    @Override
    public boolean checkUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = getOne(wrapper);
        return user != null;
    }

    @Override
    public boolean checkEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        User user = getOne(wrapper);
        return user != null;
    }

    @Override
    public boolean login(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
        return true;
    }

    @Override
    public boolean getActiveCode(String email) {
        Random random = new Random();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < CODE_COUNT; i++) {
            result.append(random.nextInt(10));
        }
        // session存入验证码，以email作为name，验证码作为value
        ServletUtils.getRequest().getSession().setAttribute(email, result.toString());
        mailService.sendSimpleMail(email, result.toString());
        return true;
    }

    @Override
    public boolean checkActiveCode(String email, String activeCode) {
        // 通过email来从session中获取验证码，再和用户输入的验证码进行比对
        HttpSession session = ServletUtils.getRequest().getSession();
        String active = (String) session.getAttribute(email);
        return active.equals(activeCode);
    }

    @Override
    public boolean modifyPassword(String email, String password) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password", password).eq("email", email);
        return this.update(updateWrapper);
    }

    @Override
    public boolean logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return true;
    }

    @Override
    public User getCurrentUser() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", QZ_IdUtils.getUserId());
        User user = getOne(wrapper);
        // 当前用户的密码设置为空，不返回给前端
        user.setPassword(null);
        return user;
    }
}
