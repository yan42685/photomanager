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
    @ApiOperation("检查用户名是否重复")
    @GetMapping("register/checkUsername")
    public JsonWrapper<Boolean> checkUsername(String username){
        return new JsonWrapper<>(userService.checkUsername(username));
    }

    @ApiOperation("检查邮箱是否重复")
    @GetMapping("register/checkEmail")
    public JsonWrapper<Boolean> checkEmail(String email){
        return new JsonWrapper<>(userService.checkEmail(email));
    }
}
