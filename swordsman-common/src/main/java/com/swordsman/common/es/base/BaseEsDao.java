package com.swordsman.common.es.base;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:47
 * Base ES Dao
 */
@NoRepositoryBean
public interface BaseEsDao<T extends BaseEsEntity> extends ElasticsearchRepository<T,String> {
}
