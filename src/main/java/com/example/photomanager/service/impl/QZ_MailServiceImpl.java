package com.example.photomanager.service.impl;

import com.example.photomanager.service.QZ_MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author 小朝
 * @date 2020/4/30
 **/
@Service
public class QZ_MailServiceImpl implements QZ_MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 从配置文件中读取发送者的邮箱地址
     */
    @Value("${spring.mail.from}")
    private String from;

    /**
     * 发送文本邮件
     * @param to 收件人
     * @param activeCode 验证码
     */
    @Override
    public void sendSimpleMail(String to,String activeCode) {
        StringBuilder builder = new StringBuilder();
        String content = builder.append("您的验证码为：").append(activeCode).append("。").toString();
        SimpleMailMessage message = new SimpleMailMessage();
        // 从哪发送
        message.setFrom(from);
        // 发送给谁
        message.setTo(to);
        // 主题
        message.setSubject("验证码邮件");
        // 内容
        message.setText(content);
        javaMailSender.send(message);
    }
}
