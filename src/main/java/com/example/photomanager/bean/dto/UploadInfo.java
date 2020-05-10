package com.example.photomanager.bean.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;

/**
 *  @author : gsj
 */

@Api("图片上传信息")
@Data
public class UploadInfo implements Serializable {

    private static final long serialVersionUID = 1325117125807346193L;

    @ApiModelProperty("图片文件")
    private MultipartFile file;

    @ApiModelProperty(value = "图片名称")
    private String name;

    @ApiModelProperty(value = "属于哪个相册")
    private Long albumId;

    @ApiModelProperty(value = "上传图片的用户")
    private Long userId;

}
