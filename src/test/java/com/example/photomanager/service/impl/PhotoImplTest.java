package com.example.photomanager.service.impl;


import com.example.photomanager.bean.dto.UploadInfo;
import com.example.photomanager.service.PhotoService;
import com.example.photomanager.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;


@SpringBootTest
public class PhotoImplTest {
    @Autowired
    PhotoService photoService;

    @Test
    public void test1() {
        UploadInfo info = new UploadInfo();
        info.setAlbumId(12L);
        info.setName("qz");
        info.setUserId(1L);
        info.setFile(new File("D:/imgs/i1.png"));
        photoService.uploadPhoto(info);
    }
}
