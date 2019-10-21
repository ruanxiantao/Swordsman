package com.swordsman.common.redis;

/**
 * @Author DuChao
 * @Date 2019-10-21 11:58
 * Redis 缓存前缀常量类
 */
public interface RedisConstant {

    String MYSQL = "MYSQL_";

    String MONGO = "MONGO_";

    String ES = "ElasticSearch_";

    String SMS_PREFIX = "SMS_";

    long EXPIRE_TIMEOUT = 10;
}
