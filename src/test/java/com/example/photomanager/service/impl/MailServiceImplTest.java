package com.example.photomanager.service.impl;

import com.example.photomanager.service.QZ_MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 小朝
 * @date 2020/4/30
 **/
@SpringBootTest
public class MailServiceImplTest {
    @Autowired
    private QZ_MailService qz_mailService;

    @Test
    public void test(){
        qz_mailService.sendSimpleMail("541099558@qq.com","123456");
    }
}
