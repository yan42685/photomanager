package com.example.photomanager.controller;

import com.example.photomanager.common.JsonWrapper;
import com.example.photomanager.enums.UserInfoEnum;
import com.example.photomanager.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户行为")
@RequestMapping("api/user")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("set")
    public JsonWrapper<Boolean> modifyInfo(UserInfoEnum field) {
        return new JsonWrapper<>(userService.modifyInfo(field));
    }
}
