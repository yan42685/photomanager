package com.example.photomanager.service;

/**
 * @author 小朝
 * @date 2020/4/30
 **/
public interface QZ_MailService {
    /**
     * 发送文本邮件
     * @param to 收件人
     * @param activeCode 验证码
     */
    void sendSimpleMail(String to,String activeCode);
}
