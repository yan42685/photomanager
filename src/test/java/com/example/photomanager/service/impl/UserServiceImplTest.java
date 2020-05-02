package com.example.photomanager.service.impl;

import com.example.photomanager.bean.dto.RegistryInfo;
import com.example.photomanager.bean.entity.User;
import com.example.photomanager.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Test
    void checkTestEnvironment() {
        Assertions.assertTrue(true);  // 应该总是返回true, 排除测试环境的问题
    }

    @Autowired
    UserService userService;

    @Transactional
    @Test
    void modifyInfo() {
        String nickName = "昵称";
        Long id = 9981L;
        User user = new User();
        user.setNickname(nickName);
        user.setId(id);
        userService.save(user);

        User findUser = userService.getById(id);
        Assertions.assertEquals(findUser.getNickname(), nickName);
    }

    @Test
    void register() {

    }
}
    