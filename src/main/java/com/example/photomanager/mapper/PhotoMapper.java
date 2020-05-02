package com.example.photomanager.mapper;

import com.example.photomanager.bean.entity.Photo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author alex
 */
public interface PhotoMapper extends BaseMapper<Photo> {
    /**
     * @param ids
     * @return 得到有关图片的imageKey集合
     */
    List<String> getImageKeysByIds(List<Long> ids);
}
