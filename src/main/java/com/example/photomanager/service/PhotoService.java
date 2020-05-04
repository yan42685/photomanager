package com.example.photomanager.service;

import com.example.photomanager.bean.dto.UploadInfo;
import com.example.photomanager.bean.entity.Photo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.photomanager.bean.dto.PhotoESInfo;
import com.example.photomanager.bean.vo.PhotoInfo;

import java.util.List;

/**
 * @author LuckyCurve
 */
public interface PhotoService extends IService<Photo> {
    /**
     * 模糊查询，根据当前用户的图片查询，返回封装了的VO对象
     * @param message 查询的关键字
     * @return  查询结果，封装成了VO对象
     */
    List<PhotoInfo> fuzzyQuery(String message);

    /**
     * 单独的ES模糊查询，没有对返回值进行封装
     * @param message 查询的关键字
     * @return 查询结果，没封装成VO对象
     */
    List<PhotoESInfo> fuzzyQueryES(String message);

    /**
     * 对ES进行写入或者更新数据操作
     * @param photoESInfo 需要写入或者更新的数据
     * @return  是否成功
     */
    Boolean addOrUpdatePhotoToES(PhotoESInfo photoESInfo);

    /**
     * 删除ES数据
     * @param photoId 图片id
     * @return 操作是否成功
     */
    Boolean deletePhotoToES(Long photoId);

    /**
     * 查询相册下的图片
     * @param albumId 相册id
     * @return  相册下的所有图片，封装成了VO对象
     */
    List<PhotoInfo> query(Long albumId);

    /**
     * 根据图片id查询图片详细信息，并封装成VO对象形式
     * @param id 图片id
     * @return 并封装成VO对象形式
     */
    PhotoInfo queryById(Long id);

    /**
     * 修改图片信息，只需要提供图片id和更新的name
     * @param photoInfo 需要修改的信息
     * @return 操作是否成功
     */
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
