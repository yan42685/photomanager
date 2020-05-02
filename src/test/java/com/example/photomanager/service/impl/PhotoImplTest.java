package com.example.photomanager.service.impl;


import com.example.photomanager.bean.dto.UploadInfo;
import com.example.photomanager.mapper.PhotoMapper;
import com.example.photomanager.service.PhotoService;
import com.example.photomanager.service.UserService;
import com.example.photomanager.util.QiniuUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class PhotoImplTest {
    @Autowired
    PhotoService photoService;

    @Autowired
    PhotoMapper mapper;
    /**
     *  上传测试
     */
    @Test
    public void test1() {
        UploadInfo info = new UploadInfo();
        info.setAlbumId(12L);
        info.setName("qzz");
        info.setUserId(1L);
        info.setFile(new File("D:/p1.png"));
        photoService.uploadPhoto(info);
    }

    /**
     *  下载测试
     */
    @Test
    public void test2() {
        photoService.downloadPhoto(1256239696246300674L);
    }


    /**
     *  删除测试
     */
    @Test
    public void test3(){
        List<Long> longList = new ArrayList<>();
        longList.add(1256454204629803009L);
        longList.add(1256454842503774209L);
        photoService.deletePhotosFromRecycleBin(longList);
    }

    /**
     *  批量还原
     */
    @Test
    public void test4(){
        List<Long> longList = new ArrayList<>();
        longList.add(1256460787665399809L);
        longList.add(1256460847895601153L);
        longList.add(1256461168675938306L);
        photoService.deletePhotos(longList);
    }


}
