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
 * @date 2020/5/6 10:45
 */
@ApiModel("创建相册")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumAddInfo implements Serializable {
    private static final long serialVersionUID = 3247019822632244195L;

    @ApiModelProperty("相册名称")
    private String name;

    @ApiModelProperty("相册描述")
    private String desc;

}
