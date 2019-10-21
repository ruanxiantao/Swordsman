package com.swordsman.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 11:31
 * In 范围区间实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "In 条件，例: age : 5岁、6岁、7岁...")
public class InCondition {

    @ApiModelProperty(value = "需要查询的字段")
    private String field;

    @ApiModelProperty(value = "值集合，形式为数组")
    private List<Object> value;

}
