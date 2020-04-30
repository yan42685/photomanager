package com.example.photomanager.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author LuckyCurve
 * @date 2020/4/30 17:19
 */
@ApiModel("照片信息")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotoInfo implements Serializable {
    private static final long serialVersionUID = 4789935060997918207L;

    @ApiModelProperty(value = "图片ID")
    private Long id;

    @ApiModelProperty(value = "图片名称")
    private String name;

    @ApiModelProperty(value = "图片存储路径")
    private String url;

    @ApiModelProperty(value = "从属相册")
    private Long albumId;
}
