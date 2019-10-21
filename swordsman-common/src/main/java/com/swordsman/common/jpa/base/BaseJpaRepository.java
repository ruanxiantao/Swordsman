package com.swordsman.common.jpa.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @Author DuChao
 * @Date 2019-10-21 11:55
 * Jpa Base Repository
 */
@NoRepositoryBean
public interface BaseJpaRepository<T extends BaseJpaEntity> extends JpaRepository<T, String>, JpaSpecificationExecutor<T> {

}
