package com.example.photomanager.bean.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author alex
 */
@ApiModel("注册信息")
@Data
public class RegistryInfo implements Serializable {
    private static final long serialVersionUID = 8626445077750529131L;
    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "验证码")
    private String verification;
}
