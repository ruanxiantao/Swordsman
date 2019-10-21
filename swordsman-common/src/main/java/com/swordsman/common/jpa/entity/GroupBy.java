package com.swordsman.common.jpa.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:08
 * 多功能通用分组实体
 */
@Data
@ToString
public class GroupBy {

    /**
     * 进行分组的字段
     */
    private String field;

}
