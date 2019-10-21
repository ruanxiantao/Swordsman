package com.swordsman.user.pojo;

import com.swordsman.common.jpa.base.BaseJpaEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author DuChao
 * @Date 2019-10-21 14:42
 * 用户角色关联表
 */
@Getter
@Setter
@Entity
@ToString
@Table(name = "sys_user_role")
@ApiModel(description = "用户角色关联表")
public class UserRole extends BaseJpaEntity {

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "角色Id")
    private String roleId;
}
