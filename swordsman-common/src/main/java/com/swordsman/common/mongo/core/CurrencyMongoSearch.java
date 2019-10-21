package com.swordsman.common.mongo.core;

import com.swordsman.common.base.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:14
 * Mongo 多功能通用查询实体
 */
@Data
@ToString
public class CurrencyMongoSearch implements Serializable {

    private List<InCondition> in;

    private List<Condition> query;

    private List<Condition> like;

    private List<Between> between;

    private DateParam dateParam;

    private List<Condition> not;

    private List<InCondition> notIn;

    private PageRequest pageOrder;

    private List<String> source;

}
