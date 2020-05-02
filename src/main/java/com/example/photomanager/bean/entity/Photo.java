package com.example.photomanager.bean.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author alex
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Photo对象", description="")
public class Photo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "图片ID")
    private Long id;

    @ApiModelProperty(value = "图片名称")
    private String name;

    @ApiModelProperty(value = "图片存储路径")
    private String url;

    @ApiModelProperty(value = "属于哪个相册")
    private Long albumId;

    @ApiModelProperty(value = "0代表未回收,1代表已回收")
    private Boolean isRecycle;

    @ApiModelProperty(value = "图片上传时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "图片信息更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "上传图片的用户")
    private Long userId;

    @ApiModelProperty(value = "图片在七牛云中的key(查找时用到)")
    private String imageKey;
}
