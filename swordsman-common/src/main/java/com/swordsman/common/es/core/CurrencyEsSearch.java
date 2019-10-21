package com.swordsman.common.es.core;

import com.swordsman.common.base.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:47
 * ES 多功能通用查询条件实体
 */
@Data
@ToString
public class CurrencyEsSearch implements Serializable {

    // 需要进行分词检索的数据
    private List<Condition> keyword;

    // 多条件
    private List<InCondition> in;

    // 查询单条件不分词
    private List<Condition> query;

    // 模糊检索
    private List<Condition> like;

    // 范围查询
    private List<Between> between;

    // 时间搜索
    private DateParam dateParam;

    // 排除
    private List<Condition> not;

    // 分页排序
    private PageRequest pageOrder;

    // 高亮字段
    private List<Highlight> highlight;

    // 返回字段自定义
    private String[] source;

}
