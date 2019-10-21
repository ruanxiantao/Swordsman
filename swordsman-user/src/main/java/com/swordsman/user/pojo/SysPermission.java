package com.swordsman.user.pojo;

import com.swordsman.common.jpa.base.BaseJpaEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @Author DuChao
 * @Date 2019-10-21 16:31
 * 系统权限
 */
@Getter
@Setter
@Entity
@ToString
@Table(name = "sys_permission")
@ApiModel(description = "系统权限")
public class SysPermission extends BaseJpaEntity {

    @ApiModelProperty(value = "权限名")
    @NotBlank(message = "权限名不能为空")
    private String name;

    @ApiModelProperty(value = "类型为页面时，代表前端路由地址，类型为按钮时，代表后端接口地址")
    @NotBlank(message = "url 不能为空")
    private String url;

    @ApiModelProperty(value = "权限类型 1 页面 | 2 按钮")
    @NotEmpty(message = "权限类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "权限表达式")
    private String permission;

    @ApiModelProperty(value = "后端接口访问方式")
    private String method;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "父权限 Id")
    private String parentId;

}
