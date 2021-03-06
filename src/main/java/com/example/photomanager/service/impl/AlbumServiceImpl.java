package com.example.photomanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.photomanager.bean.dto.AlbumAddInfo;
import com.example.photomanager.bean.dto.AlbumCoverModify;
import com.example.photomanager.bean.dto.AlbumModifyInfo;
import com.example.photomanager.bean.entity.Album;
import com.example.photomanager.bean.vo.AlbumInfo;
import com.example.photomanager.bean.vo.PhotoInfo;
import com.example.photomanager.common.KnownException;
import com.example.photomanager.enums.ExceptionEnum;
import com.example.photomanager.mapper.AlbumMapper;
import com.example.photomanager.service.AlbumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.photomanager.service.PhotoService;
import com.example.photomanager.util.FileUtils;
import com.example.photomanager.util.QZ_IdUtils;
import com.example.photomanager.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author LuckyCurve
 */
@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {

    @Autowired
    PhotoService photoService;
    @Autowired
    AlbumMapper albumMapper;

    @Override
    public List<AlbumInfo> getCurrentAlbum() {
        Long userId = QZ_IdUtils.getUserId();

        QueryWrapper<Album> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<Album> list = list(wrapper);

        LinkedList<AlbumInfo> albumInfos = new LinkedList<>();
        for (Album i : list) {
            albumInfos.add(AlbumInfo.parseAlbum(i));
        }
        return albumInfos;
    }

    @Override
    public Integer getAlbumCount() {
        Long userId = QZ_IdUtils.getUserId();
        return albumMapper.getAlbumCount(userId);
    }


    @Override
    public Boolean createAlbum(AlbumAddInfo albumInfo) {
        //模拟当前用户id
        Long userId = QZ_IdUtils.getUserId();

        Album album = new Album()
                .setName(albumInfo.getName())
                .setDescription(albumInfo.getDesc())
                .setUserId(userId);
        return save(album);
    }

    @Override
    public Boolean modifyAlbum(AlbumModifyInfo albumInfo) {
        //模拟当前用户id
        Long userId = QZ_IdUtils.getUserId();
        Album album = new Album()
                .setId(albumInfo.getId())
                .setDescription(albumInfo.getDesc())
                .setName(albumInfo.getName())
                .setUserId(userId);

        return updateById(album);
    }

    @Override
    public Boolean deleteAlbum(Long id) {
        //获取当前相册下的所有图片id
        List<PhotoInfo> photoList = photoService.query(id);
        LinkedList<Long> list = new LinkedList<>();
        for (PhotoInfo i : photoList) {
            list.add(i.getId());
        }

        //添加到回收站
        if (!list.isEmpty()) {
            photoService.deletePhotos(list);
        }

        return removeById(id);
    }

    @Override
    public Boolean modifyAlbumCover(AlbumCoverModify album) {
        //上传文件到七牛云
        String fileName = album.getCover().getOriginalFilename();
        if (!FileUtils.checkPictureFormat(fileName)) {
            throw new KnownException(ExceptionEnum.IMAGE_UPLOAD_FAIL);
        }
        String url = null;
        try {
            url = QiniuUtils.uploadPhoto(album.getCover().getBytes(), fileName);
        } catch (IOException e) {
            throw new KnownException(ExceptionEnum.FILE_IO_EXCEPTION);
        }
        Album album1 = getById(album.getId());
        album1.setCover(url);
        return updateById(album1);
    }

    @Override
    public Boolean updateAlbumCover(Long albumId, String photoURL) {
        Album album = getById(albumId);
        if (album.getCover() == null) {
            album.setCover(photoURL);
        }
        return updateById(album);
    }


}
