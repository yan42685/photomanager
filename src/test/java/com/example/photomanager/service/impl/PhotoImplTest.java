package com.example.photomanager.service.impl;


import com.example.photomanager.bean.dto.UploadInfo;
import com.example.photomanager.bean.dto.PhotoESInfo;
import com.example.photomanager.bean.vo.PhotoInfo;
import com.example.photomanager.mapper.PhotoMapper;
import com.example.photomanager.service.PhotoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class PhotoImplTest {
    @Autowired
    PhotoService photoService;

    @Autowired
    PhotoMapper mapper;

    /**
     * 上传测试
     */
    @Test
    public void test1() {
        UploadInfo info = new UploadInfo();
        info.setAlbumId(3L);
        info.setName("hW");
        info.setUserId(1L);
//        info.setFile(new File("D:/p1.png"));
        photoService.uploadPhoto(info);
    }

    /**
     * 下载测试
     */
    @Test
    public void test2() {
//        photoService.downloadPhoto(1L);
    }


    /**
     * 删除测试
     */
    @Test
    public void test3() {
        List<Long> longList = new ArrayList<>();
        longList.add(1L);
        longList.add(1257248337808072706L);
        photoService.deletePhotosFromRecycleBin(longList);
    }

    /**
     * 批量还原
     */
    @Test
    public void test4() {
        List<Long> longList = new ArrayList<>();
        longList.add(1256460787665399809L);
        longList.add(1256460847895601153L);
        longList.add(1256461168675938306L);
        photoService.deletePhotos(longList);
    }

    /**
     * ES的增删改查
     */
    @Test
    public void ESTest() {
        List<PhotoESInfo> list = photoService.fuzzyQueryES("2");
        for (PhotoESInfo photoESInfo : list) {
            System.out.println(photoESInfo);
        }
    }

    /**
     * 对图片的修改，查询操作测试
     */
    @Test
    public void test5() {
        //查询album为12的所有图片
        List<PhotoInfo> list = photoService.query(12L);
        Assertions.assertEquals(list.size(),2);

        //修改图片id为1256594971134320641的name
        //PhotoInfo info = PhotoInfo.builder().id(1256594971134320641L).name("夏末秋凉").build();
        //photoService.modifyPhoto(info);

    }

}
