package com.swordsman.user.pojo;

import com.swordsman.common.jpa.base.BaseJpaEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author DuChao
 * @Date 2019-10-21 16:44
 * 角色权限关联表
 */
@Getter
@Setter
@Entity
@ToString
@Table(name = "sys_role_permission")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "角色权限关联表")
public class RolePermission extends BaseJpaEntity {

    @ApiModelProperty(value = "角色Id")
    private String roleId;

    @ApiModelProperty(value = "权限Id")
    private String permissionId;

}
