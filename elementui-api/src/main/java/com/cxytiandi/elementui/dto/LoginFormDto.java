package com.cxytiandi.elementui.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录pageObject")
public class LoginFormDto {
    @ApiModelProperty("账号")
    private String username;
    @ApiModelProperty("密码")
    private String password;


}
