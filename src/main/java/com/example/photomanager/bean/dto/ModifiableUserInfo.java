package com.example.photomanager.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户可以修改的信息
 *
 * @author alex
 */
@ApiModel("可修改的用户信息")
@Data
public class ModifiableUserInfo implements Serializable {
    private static final long serialVersionUID = 9035637706031560357L;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "昵称")
    private String nickname;
}
