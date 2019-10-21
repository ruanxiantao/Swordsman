package com.swordsman.common.mongo.constant;

/**
 * @Author DuChao
 * @Date 2019-08-27 15:56
 * 查询常量类
 */
public interface SearchConstant {

    /**
     * 查询常量
     */
    String SEARCH_ID = "_id";
    String SEARCH_LIKE_LEFT = "^.*";
    String SEARCH_LIKE_RIGHT = ".*$";

    /**
     * 分组常量
     */
    String GROUP_SUM = "sum";
    String GROUP_AVG = "avg";
    String GROUP_MAX = "max";
    String GROUP_MIN = "min";
    String GROUP_COUNT = "count";
    String GROUP_FIRST = "first";
    String GROUP_LAST = "last";
    String GROUP_ADD = "add";

}
