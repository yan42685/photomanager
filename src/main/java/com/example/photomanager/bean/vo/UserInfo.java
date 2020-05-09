package com.example.photomanager.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * @author alex
 */
@ApiModel("用户信息")
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "邮箱,用于找回密码")
    private String email;

    @ApiModelProperty(value = "头像URL")
    private String avatar;

    @ApiModelProperty(value = "昵称")
    private String nickname;

}
