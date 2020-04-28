package com.example.photomanager.bean.entity;

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
@ApiModel(value="Album对象", description="")
public class Album implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "相册ID")
    private Long id;

    @ApiModelProperty(value = "相册名称")
    private String name;

    @ApiModelProperty(value = "相册描述")
    private String description;

    @ApiModelProperty(value = "创建相册的用户")
    private Long userId;

    @ApiModelProperty(value = "封面:如果不设置,默认相册里的第一张照片")
    private String cover;


}
