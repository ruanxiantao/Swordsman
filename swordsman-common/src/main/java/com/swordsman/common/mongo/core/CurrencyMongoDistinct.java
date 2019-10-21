package com.swordsman.common.mongo.core;

import lombok.Data;
import lombok.ToString;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:17
 * 多功能去重实体
 */
@Data
@ToString
public class CurrencyMongoDistinct {

    private CurrencyMongoSearch currencyMongoSearch;

    private String distinct;
}
