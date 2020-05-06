package com.example.photomanager.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author LuckyCurve
 * @date 2020/5/6 10:54
 */
@ApiModel("修改相册信息")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumModifyInfo implements Serializable {
    private static final long serialVersionUID = 6389257095047516452L;

    @ApiModelProperty("要修改的相册id")
    private Long id;

    @ApiModelProperty("相册名称")
    private String name;

    @ApiModelProperty("相册描述")
    private String desc;
}
