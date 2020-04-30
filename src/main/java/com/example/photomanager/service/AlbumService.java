package com.example.photomanager.service;

import com.example.photomanager.bean.entity.Album;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.photomanager.bean.vo.AlbumInfo;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * @author LuckyCurve
 * @date 2020/4/30 13:30
 */
public interface AlbumService extends IService<Album> {

    /**
     * @return 获取当前用户的所有相册
     */
    List<AlbumInfo> getCurrentAlbum();

    Boolean createAlbum(AlbumInfo albumInfo);

    Boolean modifyAlbum(AlbumInfo album);

    Boolean deleteAlbum(Long id);
}
