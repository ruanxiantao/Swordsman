package com.swordsman.user.dao;

import com.swordsman.common.jpa.base.BaseJpaRepository;
import com.swordsman.user.pojo.RolePermission;

import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 17:10
 * RolePermission Dao
 */
public interface RolePermissionDao extends BaseJpaRepository<RolePermission> {

    List<RolePermission> findByRoleId(String roleId);


}
