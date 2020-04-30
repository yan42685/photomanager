package com.example.photomanager.service.impl;

import com.example.photomanager.bean.entity.Photo;
import com.example.photomanager.bean.vo.PhotoInfo;
import com.example.photomanager.mapper.PhotoMapper;
import com.example.photomanager.service.PhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LuckyCurve
 * @date 2020/4/30 17:18
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements PhotoService {

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
}
