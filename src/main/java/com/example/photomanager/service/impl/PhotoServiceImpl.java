package com.example.photomanager.service.impl;

import com.example.photomanager.bean.dto.UploadInfo;
import com.example.photomanager.bean.entity.Photo;
import com.example.photomanager.bean.vo.PhotoInfo;
import com.example.photomanager.common.KnownException;
import com.example.photomanager.enums.ExceptionEnum;
import com.example.photomanager.mapper.PhotoMapper;
import com.example.photomanager.service.PhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photomanager.util.FileUtils;
import com.example.photomanager.util.QZ_IdUtils;
import com.example.photomanager.util.QiniuUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LuckyCurve
 * @date 2020/4/30 17:18
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements PhotoService {

    @Autowired
    PhotoMapper photoMapper;

    /**
     * 模糊查询
     */
    @Override
    public List<PhotoInfo> fuzzyQuery(String message) {
        return null;
    }

    /**
     * 查询相册下的图片
     */
    @Override
    public List<PhotoInfo> query(Long albumId) {
        return null;
    }

    /**
     * 修改图片信息
     */
    @Override
    public Boolean modifyPhoto(PhotoInfo photoInfo) {
        return null;
    }


    /**
     * 上传图片,待完善:断电上传,多线程上传
     * @return true代表上传成功,false代表上传失败
     */
    @Override
    public Boolean uploadPhoto(UploadInfo uploadInfo) {
        String fileName = uploadInfo.getFile().getName();
        if (!FileUtils.checkPictureFormat(fileName)){
            throw new KnownException(ExceptionEnum.IMAGE_UPLOAD_FAIL);
        }
        String url = QiniuUtils.uploadPhoto(uploadInfo.getFile());
        Photo photo = new Photo();
        BeanUtils.copyProperties(uploadInfo,photo);
        photo.setUrl(url);
        photo.setUserId(QZ_IdUtils.getUserId());
        photo.setCreateTime(LocalDateTime.now());
        photo.setUpdateTime(LocalDateTime.now());
        photo.setImageKey(fileName);
        if (photoMapper.insert(photo) > 0){
            return true;
        }
        return false;
    }

    /**
     * 下载图片
     * @param id 图片id
     */
    @Override
    public Boolean downloadPhoto(Long id) {
        String url = photoMapper.selectById(id).getUrl();
        QiniuUtils.downloadPhoto(url);
        return true;
    }

    /**
     *  删除图片到回收站
     */
    @Override
    public Boolean deletePhoto(Long id) {
        Photo photo = photoMapper.selectById(id);
        photo.setIsRecycle(true);
        photoMapper.updateById(photo);
        return true;
    }

    /**
     *  删除多张图片到回收站
     */
    @Override
    public Boolean deletePhotos(List<Long> ids) {
        List<Photo> photos = photoMapper.selectBatchIds(ids);
        for (Photo photo:photos){
            photo.setIsRecycle(true);
            photoMapper.updateById(photo);
        }
        return true;
    }

    /**
     * @return 从回收站中删除一张图片
     */
    @Override
    public Boolean deletePhotoFromRecycleBin(Long id) {
        QiniuUtils.deletePhoto(photoMapper.selectById(id).getImageKey());
        if (photoMapper.deleteById(id)>0){
            return true;
        }
        return false;
    }

    /**
     * @return 从回收站删除多张照片
     */
    @Override
    public Boolean deletePhotosFromRecycleBin(List<Long> ids) {
        List<String> imageKeys = photoMapper.getImageKeysByIds(ids);
        QiniuUtils.deletePhotos((imageKeys.toArray(new String[0])));
        if (photoMapper.deleteBatchIds(ids)>0){
            return true;
        }
        return false;
    }

    /**
     *  还原一张图片
     */
    @Override
    public Boolean restorePhoto(Long id) {
        Photo photo = photoMapper.selectById(id);
        photo.setIsRecycle(false);
        photoMapper.updateById(photo);
        return true;
    }

    /**
     *  还原多张图片
     */
    @Override
    public Boolean restorePhotos(List<Long> ids) {
        List<Photo> photos = photoMapper.selectBatchIds(ids);
        for (Photo photo:photos){
            photo.setIsRecycle(false);
            photoMapper.updateById(photo);
        }
        return true;
    }
}
