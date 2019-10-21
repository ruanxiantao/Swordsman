package com.swordsman.common.mongo.core;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.ToString;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:17
 * Mongo 聚合条件实体
 */
@Data
@ToString
public class CounterMongoAggregation {

    // 需要进行计算的属性
    private String field;
    // 需要计算的类型，例如sum，avg等等
    private String type;
    // 返回的字段
    private String as;

    //返回的字段如果不输入默认返回字段
    public String getAs(){
        if(StrUtil.isEmpty(this.as))
            this.as = this.field;
        return this.as;
    }

}
