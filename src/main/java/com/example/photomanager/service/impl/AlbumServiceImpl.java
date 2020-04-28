package com.example.photomanager.service.impl;

import com.example.photomanager.bean.entity.Album;
import com.example.photomanager.mapper.AlbumMapper;
import com.example.photomanager.service.AlbumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {

}
