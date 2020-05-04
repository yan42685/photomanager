package com.example.photomanager.bean.vo;

import com.example.photomanager.bean.entity.Album;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @author LuckyCurve
 * @date 2020/4/30 13:54
 */
@ApiModel("相册信息")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumInfo implements Serializable {
    private static final long serialVersionUID = -8236471644742876626L;

    @ApiModelProperty("相册id，创建相册不用传")
    private Long id;

    @ApiModelProperty("相册名称")
    private String name;

    @ApiModelProperty("相册描述")
    private String desc;

    @ApiModelProperty("相册封面URL，可以为null")
    private String cover;

    public static AlbumInfo parseAlbum(Album album) {
        return AlbumInfo.builder()
                .id(album.getId())
                .name(album.getName())
                .desc(album.getDescription())
                .cover(album.getCover()).build();
    }
}
