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
        if (!FileUtils.checkPictureFormat(uploadInfo.getFile().getName())){
            throw new KnownException(ExceptionEnum.IMAGE_UPLOAD_FAIL);
        }
        String url = QiniuUtils.uploadPhoto(uploadInfo.getFile());
        Photo photo = new Photo();
        BeanUtils.copyProperties(uploadInfo,photo);
        photo.setUrl(url);
        photo.setCreateTime(LocalDateTime.now());
        photo.setUpdateTime(LocalDateTime.now());
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
}
