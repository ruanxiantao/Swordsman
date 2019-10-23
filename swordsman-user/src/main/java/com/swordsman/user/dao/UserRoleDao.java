package com.swordsman.user.dao;

import com.swordsman.common.jpa.base.BaseJpaRepository;
import com.swordsman.user.pojo.UserRole;

import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 17:10
 * UserRole Dao
 */
public interface UserRoleDao extends BaseJpaRepository<UserRole> {

    List<UserRole> findByUserId(String userId);

}
