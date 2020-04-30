package com.example.photomanager.service;

import com.example.photomanager.bean.entity.Photo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.photomanager.bean.vo.PhotoInfo;

import java.util.List;

public interface PhotoService extends IService<Photo> {

    List<PhotoInfo> fuzzyQuery(String message);

    List<PhotoInfo> query(Long albumId);

    Boolean modifyPhoto(PhotoInfo photoInfo);
}
