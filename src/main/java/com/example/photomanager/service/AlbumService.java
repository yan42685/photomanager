package com.example.photomanager.service;

import com.example.photomanager.bean.entity.Album;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.photomanager.bean.vo.AlbumInfo;

import java.util.List;

/**
 * @author LuckyCurve
 * @date 2020/4/30 13:30
 */
public interface AlbumService extends IService<Album> {

    /**
     * 获取当前用户的所有相册
     */
    List<AlbumInfo> getCurrentAlbum();

    /**
     * 创建相册
     * @param albumInfo 相册详细信息
     * @return 是否创建成功
     */
    Boolean createAlbum(AlbumInfo albumInfo);


    /**
     * 修改相册
     * @param album 相册详细信息
     * @return 是否修改成功
     */
    Boolean modifyAlbum(AlbumInfo album);

    /**
     * 根据id删除相册，以及所属的照片
     * @param id 相册id
     * @return 是否删除成功
     */
    Boolean deleteAlbum(Long id);
}
