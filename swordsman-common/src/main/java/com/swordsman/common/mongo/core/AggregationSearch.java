package com.swordsman.common.mongo.core;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:17
 * Mongo 聚合查询实体
 */
@Data
@ToString
public class AggregationSearch {

    // 使用通用查询的条件，还有分页以及源数据
    private CurrencyMongoSearch currencyMongoSearch;

    // 进行聚合的字段
    private String groupBy;

    // 各种条件，例如sum，avg等等
    private List<CounterMongoAggregation> counter;

}
