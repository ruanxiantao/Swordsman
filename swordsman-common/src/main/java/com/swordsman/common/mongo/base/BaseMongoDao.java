package com.swordsman.common.mongo.base;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:18
 * Base Mongo Dao
 */
@NoRepositoryBean
public interface BaseMongoDao<T extends BaseMongoEntity> extends MongoRepository<T, String> {
}
