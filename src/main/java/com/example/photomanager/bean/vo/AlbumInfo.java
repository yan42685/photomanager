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

    @ApiModelProperty("相册id")
    private Long id;

    @ApiModelProperty("相册名称")
    private String name;

    @ApiModelProperty("相册描述")
    private String desc;

    @ApiModelProperty("相册封面URL")
    private String cover;

    public static AlbumInfo parseAlbum(Album album) {

        //封面默认URL
        String defaultCoverURL = "默认封面URL";

        AlbumInfo albumInfo = AlbumInfo.builder()
                .id(album.getId())
                .name(album.getName())
                .desc(album.getDescription())
                .cover(album.getCover())
                .build();

        if (album.getCover() == null) {
            albumInfo.setCover(defaultCoverURL);
        }

        return albumInfo;
    }
}
