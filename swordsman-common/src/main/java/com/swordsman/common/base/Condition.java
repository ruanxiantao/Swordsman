package com.swordsman.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author DuChao
 * @Date 2019-10-21 11:28
 * equal 条件实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "KV<键值对> 条件，例: age : 5岁")
public class Condition {

    @ApiModelProperty(value = "需要查询的字段")
    private String field;

    @ApiModelProperty(value = "需要查询的字段值")
    private Object value;

    public String getField(){
        if(field.equals("String") || field.equals("string"))
            return null;
        return this.field;
    }

}
