package com.swordsman.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @Author DuChao
 * @Date 2019-10-22 13:40
 * 请求登录实体
 */
@Data
@ToString
public class LoginRequest {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名或邮箱或手机号")
    private String usernameOrEmailOrPhone;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "记住我")
    private Boolean rememberMe = false;

}
