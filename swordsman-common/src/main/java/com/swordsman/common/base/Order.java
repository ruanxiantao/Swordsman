package com.swordsman.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * @Author DuChao
 * @Date 2019-10-21 11:31
 * 排序条件实体
 */
@Data
@ApiModel(description = "排序 条件，例: createTime 降序排列")
public class Order implements Serializable {

    @ApiModelProperty(value = "需要排序的字段")
    private String orderBy;

    @ApiModelProperty(value = "排序方式,例: DESC<降序、默认>，ASC<升序>")
    private Sort.Direction direction;

    public Order(Sort.Direction direction, String orderBy) {
        this.direction = Sort.Direction.DESC;
        this.orderBy = orderBy;
        this.direction = direction;
    }
}
