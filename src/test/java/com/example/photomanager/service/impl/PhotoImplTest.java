package com.example.photomanager.service.impl;


import com.example.photomanager.bean.dto.UploadInfo;
import com.example.photomanager.service.PhotoService;
import com.example.photomanager.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Arrays;


@SpringBootTest
public class PhotoImplTest {
    @Autowired
    PhotoService photoService;

    /**
     *  上传测试
     */
    @Test
    public void test1() {
        UploadInfo info = new UploadInfo();
        info.setAlbumId(12L);
        info.setName("邱朝敲代码");
        info.setUserId(1L);
        info.setFile(new File("D:/server/jetbrains-agent.jar"));
        photoService.uploadPhoto(info);
    }

    /**
     *  下载测试
     */
    @Test
    public void test2() {
        photoService.downloadPhoto(1256239696246300674L);
    }
}
