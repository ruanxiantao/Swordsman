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

/**
 * @Author DuChao
 * @Date 2019-10-21 16:43
 * 系统角色
 * 唯一索引 : name
 */
@Getter
@Setter
@Entity
@ToString
@Table(name = "sys_role")
@ApiModel(description = "系统角色")
public class SysRole extends BaseJpaEntity {

    @ApiModelProperty(value = "角色名")
    @NotBlank(message = "角色名不能为空")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

}
