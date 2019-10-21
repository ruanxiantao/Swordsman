package com.swordsman.common.mongo.constant;

import java.util.regex.Pattern;

/**
 * @Author DuChao
 * @Date 2019-08-27 15:58
 * Mongo 模糊匹配
 */
public class SearchMethod {

    /**
     * 正则查询，查询mongo模糊查询使用
     */
    public static Pattern pattern(String value) {
        return Pattern.compile(SearchConstant.SEARCH_LIKE_LEFT + value + SearchConstant.SEARCH_LIKE_RIGHT, Pattern.CASE_INSENSITIVE);
    }
}
