package com.example.photomanager.mapper;

import com.example.photomanager.bean.dto.PhotoESInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author LuckyCurve
 * @date 2020/5/2 14:48
 */
public interface PhotoESMapper extends CrudRepository<PhotoESInfo, Long> {

    List<PhotoESInfo> findByUserIdAndDescLike(Long userId, String desc);
}
