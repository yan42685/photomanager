package com.example.photomanager.service.impl;

import com.example.photomanager.bean.dto.AlbumAddInfo;
import com.example.photomanager.bean.dto.AlbumCoverModify;
import com.example.photomanager.bean.dto.AlbumModifyInfo;
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

    @Test
    public void testAddAlbum() {
        //创建相册
        AlbumAddInfo album = AlbumAddInfo.builder().name("夏末秋凉").desc("夏末秋凉").build();
        Boolean album1 = albumService.createAlbum(album);
        Assertions.assertTrue(album1);
    }

    @Test
    public void testModifyAlbum() {
        //修改相册
        AlbumModifyInfo info = AlbumModifyInfo.builder().id(1257865877768921089L).name("夏末秋凉").desc("简单描述").build();
        Boolean album = albumService.modifyAlbum(info);
        Assertions.assertTrue(album);
    }

    @Test
    public void getCurrentAlbum() {
        List<AlbumInfo> list = albumService.getCurrentAlbum();
        for (AlbumInfo i : list) {
            Assertions.assertNotNull(i.getCover());
        }
    }
}