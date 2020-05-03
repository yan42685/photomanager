package com.example.photomanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.photomanager.bean.entity.Album;
import com.example.photomanager.bean.vo.AlbumInfo;
import com.example.photomanager.mapper.AlbumMapper;
import com.example.photomanager.service.AlbumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author LuckyCurve
 */
@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {

    @Override
    public List<AlbumInfo> getCurrentAlbum() {
        //模拟当前用户id
        Long userId = 1L;

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
    public Boolean createAlbum(AlbumInfo albumInfo) {
        //模拟当前用户id
        Long userId = 1L;

        Album album = new Album()
                .setId(albumInfo.getId())
                .setName(albumInfo.getName())
                .setCover(albumInfo.getCover())
                .setDescription(albumInfo.getDesc())
                .setUserId(userId);
        return save(album);
    }

    @Override
    public Boolean modifyAlbum(AlbumInfo albumInfo) {
        //模拟当前用户id
        Long userId = 1L;

        Album album = new Album()
                .setId(albumInfo.getId())
                .setCover(albumInfo.getCover())
                .setDescription(albumInfo.getDesc())
                .setUserId(userId);

        return updateById(album);
    }

    @Override
    public Boolean deleteAlbum(Long id) {
        //删除相册内的图片，调用郭世杰的接口
        return removeById(id);
    }


}
