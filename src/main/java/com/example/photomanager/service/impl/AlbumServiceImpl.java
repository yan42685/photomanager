package com.example.photomanager.service.impl;

import com.example.photomanager.bean.entity.Album;
import com.example.photomanager.bean.vo.AlbumInfo;
import com.example.photomanager.mapper.AlbumMapper;
import com.example.photomanager.service.AlbumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {

    @Override
    public List<AlbumInfo> getCurrentAlbum() {
        return null;
    }

    @Override
    public Boolean createAlbum(AlbumInfo albumInfo) {
        return null;
    }

    @Override
    public Boolean modifyAlbum(AlbumInfo album) {
        return null;
    }

    @Override
    public Boolean deleteAlbum(Long id) {
        //删除相册内的图片
        return null;
    }


}
