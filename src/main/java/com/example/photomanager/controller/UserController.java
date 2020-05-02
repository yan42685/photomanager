package com.example.photomanager.controller;

import com.example.photomanager.bean.dto.RegistryInfo;
import com.example.photomanager.bean.vo.UserInfo;
import com.example.photomanager.common.JsonWrapper;
import com.example.photomanager.enums.UserInfoEnum;
import com.example.photomanager.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 *
 */
@Api(tags = "用户行为")
@RequestMapping("api/user")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation("修改用户信息")
    @GetMapping("info/set")
    public JsonWrapper<Boolean> modifyInfo(UserInfoEnum field, String data) {
        return new JsonWrapper<>(userService.modifyInfo(field, data));
    }

    @ApiOperation("注册")
    @GetMapping("register")
    public JsonWrapper<UserInfo> register(RegistryInfo info) {
        return new JsonWrapper<>(userService.register(info));
    }


    /**
     * 通过发送ajax请求，查看是否重复
     * @param username username
     * @return 返回true或false
     */
    @ApiOperation("检查用户名是否已经存在，存在返回true，不存在返回false，注册时检查")
    @GetMapping("register/checkUsername")
    public JsonWrapper<Boolean> checkUsername(String username){
        return new JsonWrapper<>(userService.checkUsername(username));
    }

    /**
     * 通过发送ajax请求，查看是否已经存在
     * @param email email
     * @return 返回true或false
     */
    @ApiOperation("检查邮箱是否已经存在，存在返回true，不存在返回false，注册和找回密码时都需要检查")
    @GetMapping("register/checkEmail")
    public JsonWrapper<Boolean> checkEmail(String email){
        return new JsonWrapper<>(userService.checkEmail(email));
    }

    @ApiOperation("登录")
    @GetMapping("login")
    public JsonWrapper<Boolean> login(String username,String password,Boolean rememberMe){
        return new JsonWrapper<>(userService.login(username,password,rememberMe));
    }

    /**
     * 发送ajax请求，发送验证码
     * @param email 邮箱
     * @param session session
     * @return 不出异常，则邮件发送成功
     */
    @ApiOperation("发送验证码")
    @GetMapping("getActiveCode")
    public JsonWrapper<Boolean> getActiveCode(String email, HttpSession session){
        return new JsonWrapper<>(userService.getActiveCode(email,session));
    }

    /**
     * 发送ajax请求，比对验证码
     * @param email 用户email
     * @param activeCode 用户输入的activeCode
     * @param session session
     * @return 返回是否验证成功，成功则跳转到输入新密码界面,同时将邮箱也带过去显示
     */
    @ApiOperation("核实验证码")
    @GetMapping("checkActiveCode")
    public JsonWrapper<Boolean> checkActiveCode(String email, String activeCode, HttpSession session){
        return new JsonWrapper<>(userService.checkActiveCode(email,activeCode,session));
    }

    /**
     * 修改密码
     * @param email email
     * @param password 新密码
     * @return 返回是否修改成功
     */
    @ApiOperation("输入新密码")
    @GetMapping("modifyPassword")
    public JsonWrapper<Boolean> modifyPassword(String email, String password){
        return new JsonWrapper<>(userService.modifyPassword(email,password));
    }
}
