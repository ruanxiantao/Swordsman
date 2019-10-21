package com.swordsman.common.es.core;

import lombok.Data;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:46
 * 高亮条件实体
 */
@Data
public class Highlight {

    // 高亮字段
    private String highlightBy;

}