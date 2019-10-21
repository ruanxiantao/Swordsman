package com.swordsman.common.jpa.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author DuChao
 * @Date 2019-10-21 11:54
 * Jpa Base Entity
 */
@Data
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseJpaEntity implements Serializable {

    @Id
    @Column(name = "id")
    @ApiModelProperty(hidden = true)
    protected String id;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(hidden = true)
    protected Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    @ApiModelProperty(hidden = true)
    protected Date updateTime;
}
