package com.example.photomanager.mapper;

import com.example.photomanager.bean.entity.Album;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface AlbumMapper extends BaseMapper<Album> {
    @Select("select count(*) from album where user_id = #{userId}")
    Integer getAlbumCount(Long userId);
}
