package com.swordsman.common.jpa.entity;

import com.swordsman.common.base.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 11:35
 * Jpa 多功能通用查询条件
 */
@Data
@ToString
public class CurrencyJpaSearch implements Serializable {

    private List<InCondition> in;

    private List<Condition> query;

    private List<Condition> like;

    private List<Between> between;

    private DateParam dateParam;

    private List<Condition> not;

    private PageRequest pageOrder;
}
