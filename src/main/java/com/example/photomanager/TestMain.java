package com.example.photomanager;


import com.example.photomanager.bean.dto.UploadInfo;
import com.example.photomanager.service.PhotoService;
import com.example.photomanager.service.impl.PhotoServiceImpl;
import com.example.photomanager.util.QiniuUtils;

import java.io.File;

/**
 * 用于快速验证想法
 */
public class TestMain {
    public static void main(String[] args) {
        //System.out.println("hello world");
        PhotoService photoService = new PhotoServiceImpl();
        UploadInfo info = new UploadInfo();
        info.setAlbumId(12L);
        info.setName("邱朝敲代码");
        info.setUserId(1L);
        info.setFile(new File("D:/p1.png"));
        photoService.uploadPhoto(info);
    }
}
