package com.swordsman.common.mongo.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:18
 * Base Mongo Entity
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseMongoEntity implements Serializable {

    @Id
    @ApiModelProperty(hidden = true)
    protected String id;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    protected Date createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(hidden = true)
    protected Date updateTime;

}
