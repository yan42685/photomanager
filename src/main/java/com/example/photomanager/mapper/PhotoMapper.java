package com.example.photomanager.mapper;

import com.example.photomanager.bean.entity.Photo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.photomanager.bean.vo.PhotoInfo;
import io.swagger.models.auth.In;

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

    /**
     *  查询回收站中的所有图片
     * @param userId :用户id
     */
    List<PhotoInfo> listAllPhotosInBin(Long userId);
}
