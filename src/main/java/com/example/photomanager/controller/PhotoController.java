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

    @ApiOperation("上传图片,图片格式仅支持:png,jpg,gif")
    @PostMapping("/uploadPhoto")
    public JsonWrapper<Boolean> uploadPhoto(UploadInfo uploadInfo) {
        return new JsonWrapper<>(photoService.uploadPhoto(uploadInfo));
    }

    @ApiOperation("下载图片,默认下载路径:D:/photos/")
    @GetMapping("/downloadPhoto/{id}")
    @ApiImplicitParam(name="id", value = "图片的id", dataType = "Long")
    public JsonWrapper<Boolean> downloadPhoto(@PathVariable("id") Long id) {
        return new JsonWrapper<>(photoService.downloadPhoto(id));
    }

    @ApiOperation("删除图片到回收站")
    @DeleteMapping("/deletePhoto/{id}")
    @ApiImplicitParam(name="id", value = "图片的id", dataType = "Long")
    public JsonWrapper<Boolean> deletePhoto(@PathVariable("id") Long id) {
        return new JsonWrapper<>(photoService.deletePhoto(id));
    }

    @ApiOperation("删除多张图片到回收站")
    @DeleteMapping("/deletePhotos")
    @ApiImplicitParam(name="ids", value = "要删除的图片的id组成的list,eg:1,2,3,8")
    public JsonWrapper<Boolean> deletePhotos(@RequestParam("ids")List<Long> ids) {
        return new JsonWrapper<>(photoService.deletePhotos(ids));
    }

    @ApiOperation("从回收站中删除一张图片")
    @DeleteMapping("/deletePhotoFromRecycleBin/{id}")
    @ApiImplicitParam(name="id", value = "图片的id", dataType = "Long")
    public JsonWrapper<Boolean> deletePhotoFromRecycleBin(@PathVariable("id") Long id) {
        return new JsonWrapper<>(photoService.deletePhotoFromRecycleBin(id));
    }

    @ApiOperation("从回收站删除多张照片")
    @DeleteMapping("/deletePhotosFromRecycleBin")
    @ApiImplicitParam(name="ids", value = "要删除的图片的id组成的list,eg:1,2,3,8")
    public JsonWrapper<Boolean> deletePhotosFromRecycleBin(@RequestParam("ids") List<Long> ids) {
        return new JsonWrapper<>(photoService.deletePhotosFromRecycleBin(ids));
    }

    @ApiOperation("从回收站还原一张图片")
    @PutMapping("/restorePhoto/{id}")
    @ApiImplicitParam(name="id", value = "图片的id", dataType = "Long")
    public JsonWrapper<Boolean> restorePhoto(@PathVariable("id")Long id){
        return new JsonWrapper<>(photoService.restorePhoto(id));
    }

    @ApiOperation("从回收站还原多张图片")
    @PutMapping("/restorePhotos")
    @ApiImplicitParam(name="ids", value = "要还原的图片的id组成的list,eg:1,2,3,8")
    public JsonWrapper<Boolean> restorePhotos(@RequestParam("ids")List<Long> ids){
        return new JsonWrapper<>(photoService.restorePhotos(ids));
    }
}

