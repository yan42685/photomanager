package com.example.photomanager.controller;

import com.example.photomanager.bean.dto.UploadInfo;
import com.example.photomanager.bean.vo.PhotoInfo;
import com.example.photomanager.common.JsonWrapper;
import com.example.photomanager.service.PhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author LuckyCurve
 * @date 2020/4/30 17:18
 */
@Api(tags = "图片操作")
@RequestMapping("api/photo")
@RestController
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @ApiOperation("查询当前相册下的所有照片")
    @GetMapping("/")
    @ApiImplicitParam(name = "albumId", value = "相册Id", dataType = "Long")
    public JsonWrapper<List<PhotoInfo>> query(Long albumId) {
        return new JsonWrapper<>(photoService.query(albumId));
    }

    @ApiOperation("修改图片")
    @PutMapping("/")
    public JsonWrapper<Boolean> modifyPhoto(PhotoInfo photoInfo) {
        return new JsonWrapper<>(photoService.modifyPhoto(photoInfo));
    }


    @ApiOperation("模糊查询")
    @GetMapping("/fuzzyquery")
    @ApiImplicitParam(name = "message", value = "查询关键字", dataType = "String")
    public JsonWrapper<List<PhotoInfo>> fuzzyQuery(String message) {
        return new JsonWrapper<>(photoService.fuzzyQuery(message));
    }

    @ApiOperation("上传图片")
    @PostMapping("/uploadPhoto")
    public JsonWrapper<Boolean> uploadPhoto(UploadInfo uploadInfo) {
        return new JsonWrapper<>(photoService.uploadPhoto(uploadInfo));
    }

    @ApiOperation("下载图片")
    @GetMapping("/downloadPhoto/{id}")
    @ApiImplicitParam(name="id", value = "图片的id", dataType = "Long")
    public JsonWrapper<Boolean> downloadPhoto(@PathVariable("id") Long id) {
        return new JsonWrapper<>(photoService.downloadPhoto(id));
    }
}

