package com.example.photomanager.service.impl;


import com.example.photomanager.bean.dto.UploadInfo;
import com.example.photomanager.bean.dto.PhotoESInfo;
import com.example.photomanager.bean.vo.PhotoInfo;
import com.example.photomanager.mapper.PhotoMapper;
import com.example.photomanager.service.PhotoService;
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

    /**
     * ES测试，上传更新信息
     */
    @Test
    public void addOrUpdatePhotoToES() {
        PhotoESInfo info = PhotoESInfo.builder().photoId(1L).userId(1L).desc("第二张图片").build();
        photoService.addOrUpdatePhotoToES(info);
    }

    /**
     * 模糊查询
     */
    @Test
    public void fuzzyQuery() {
        List<PhotoInfo> list = photoService.fuzzyQuery("二");
        for (PhotoInfo photoInfo : list) {
            System.out.println(photoInfo);
        }
    }

    /**
     * 删除ES数据
     */
    @Test
    public void deletePhotoToES() {
        Boolean photo = photoService.deletePhotoToES(1L);
        System.out.println(photo);
    }


}
