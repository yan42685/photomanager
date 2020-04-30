package com.example.photomanager.controller;

import com.example.photomanager.bean.entity.Album;
import com.example.photomanager.bean.vo.AlbumInfo;
import com.example.photomanager.common.JsonWrapper;
import com.example.photomanager.service.AlbumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LuckyCurve
 * @date 2020/4/30 13:27
 */
@Api(tags = "相册操作")
@RequestMapping("/api/album")
@RestController
public class AlbumController {
    @Autowired
    AlbumService albumService;

    @ApiOperation("返回当前用户的所有相册")
    @GetMapping("/")
    public JsonWrapper<List<AlbumInfo>> getCurrentAlbum() {
        return new JsonWrapper<>(albumService.getCurrentAlbum());
    }

    @ApiOperation("新建相册")
    @PostMapping("/")
    public JsonWrapper<Boolean> createAlbum(AlbumInfo album) {
        return new JsonWrapper<>(albumService.createAlbum(album));
    }

    @ApiOperation("修改相册")
    @PutMapping("/")
    public JsonWrapper<Boolean> modifyAlbum(AlbumInfo album) {
        return new JsonWrapper<>(albumService.modifyAlbum(album));
    }

    @ApiOperation("删除相册")
    @DeleteMapping("/")
    @ApiImplicitParam(name = "id",value = "相册id",dataType = "Long")
    public JsonWrapper<Boolean> deleteAlbum(Long id) {
        return new JsonWrapper<>(albumService.deleteAlbum(id));
    }
}
