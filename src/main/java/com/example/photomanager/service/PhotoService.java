package com.example.photomanager.service;

import com.example.photomanager.bean.dto.UploadInfo;
import com.example.photomanager.bean.entity.Photo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.photomanager.bean.dto.PhotoESInfo;
import com.example.photomanager.bean.vo.PhotoInfo;

import java.util.List;

public interface PhotoService extends IService<Photo> {

    List<PhotoInfo> fuzzyQuery(String message);

    Boolean addOrUpdatePhotoToES(PhotoESInfo photoESInfo);

    Boolean deletePhotoToES(Long photoId);

    List<PhotoInfo> query(Long albumId);

    PhotoInfo queryById(Long id);

    Boolean modifyPhoto(PhotoInfo photoInfo);

    Boolean uploadPhoto(UploadInfo photoInfo);

    Boolean downloadPhoto(Long id);

    Boolean deletePhoto(Long id);

    Boolean deletePhotos(List<Long> ids);

    Boolean deletePhotoFromRecycleBin(Long id);

    Boolean deletePhotosFromRecycleBin(List<Long> ids);

    Boolean restorePhoto(Long id);

    Boolean restorePhotos(List<Long> ids);
}
