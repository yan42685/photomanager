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
 * @date 2020/5/6 11:18
 */
@ApiModel("修改相册封面")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumCoverModify implements Serializable {
    private static final long serialVersionUID = 2343746458250017895L;

    @ApiModelProperty(value = "相册ID")
    private Long id;

    @ApiModelProperty(value = "封面URL")
    private String cover;
}
