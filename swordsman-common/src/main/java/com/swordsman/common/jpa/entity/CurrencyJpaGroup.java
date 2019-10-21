package com.swordsman.common.jpa.entity;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 11:38
 * Jpa 多功能 + 聚合通用查询条件实体
 */
@Data
@ToString
public class CurrencyJpaGroup {

    private CurrencyJpaSearch currencyJpaSearch;

    private List<GroupBy> groupBy;

    private List<Source> source;

    private List<GroupOrder> orders;

}
