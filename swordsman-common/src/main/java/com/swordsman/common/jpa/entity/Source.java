package com.swordsman.common.jpa.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:09
 * 查询字段
 */
@Data
@ToString
public class Source {

    private String field;
    private String type;

}
