package com.example.photomanager.service.impl;

import com.example.photomanager.bean.vo.AlbumInfo;
import com.example.photomanager.service.AlbumService;
import org.apache.shiro.util.ThreadContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author LuckyCurve
 * @date 2020/5/3 10:47
 */
@SpringBootTest
class AlbumServiceImplTest {

    @Autowired
    AlbumService albumService;

    @Resource
    org.apache.shiro.mgt.SecurityManager securityManager;

    @BeforeEach
    public void setUp() {
        ThreadContext.bind(securityManager);
    }

    /**
     * 对相册的增删改查进行测试
     */
    @Test
    public void testAlbum() {
        //创建相册
        AlbumInfo album = AlbumInfo.builder().name("夏末秋凉").desc("夏末秋凉").build();
        Boolean album1 = albumService.createAlbum(album);
        Assertions.assertTrue(album1);

        //获取当前用户所有相册
        List<AlbumInfo> list = albumService.getCurrentAlbum();
        assertFalse(list.isEmpty());

        //删除获取到的所有相册
        for (AlbumInfo i : list) {
            albumService.deleteAlbum(i.getId());
        }

        //再次获取相册，应该为null
        Assertions.assertTrue(albumService.getCurrentAlbum().isEmpty());
    }
}