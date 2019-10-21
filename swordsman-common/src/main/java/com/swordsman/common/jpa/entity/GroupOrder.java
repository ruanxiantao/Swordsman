package com.swordsman.common.jpa.entity;


import lombok.Data;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:08
 * 分组排序条件实体
 */
@Data
public class GroupOrder {

    private String filed;

    private String type;

    private String direction;

}
