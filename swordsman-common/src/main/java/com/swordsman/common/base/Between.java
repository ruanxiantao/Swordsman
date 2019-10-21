package com.swordsman.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author DuChao
 * @Date 2019-08-27 15:41
 * Between 条件实体
 */
@Data
@ApiModel(description = "between 条件，例: age 5岁到10岁之间")
public class Between {

    @ApiModelProperty(value = "需要查询的字段")
    private String field;

    @ApiModelProperty(value = "字段的起始值")
    private Object start;

    @ApiModelProperty(value = "字段的结束值")
    private Object end;

}
