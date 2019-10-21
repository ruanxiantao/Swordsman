package com.swordsman.user.pojo;

import com.swordsman.common.jpa.base.BaseJpaEntity;
import com.swordsman.common.valid.Phone;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @Author DuChao
 * @Date 2019-10-21 16:46
 * 系统用户
 * 唯一索引 username、phone
 */
@Getter
@Setter
@Entity
@ToString
@Table(name = "sys_user")
@ApiModel(description = "系统用户")
public class SysUser extends BaseJpaEntity {

    @ApiModelProperty(value = "用户名<登录，唯一>")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    @Length(min = 6,message = "密码长度不能小于6位")
    private String password;

    @ApiModelProperty(value = "昵称<取值不限>")
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "用户手机号不能为空")
    @Phone
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @NotBlank(message = "用户邮箱不能为空")
    @Email(message = "邮件格式错误")
    private String email;

    @ApiModelProperty(value = "用户性别 1 男 | 2 女")
    private Integer sex;

    @ApiModelProperty(value = "账号状态 1 启用 | 2 禁用")
    private Integer status;

}

