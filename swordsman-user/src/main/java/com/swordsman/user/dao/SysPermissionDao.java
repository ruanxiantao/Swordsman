package com.swordsman.user.dao;

import com.swordsman.common.jpa.base.BaseJpaRepository;
import com.swordsman.user.pojo.SysPermission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 17:10
 * SysPermission Dao
 */
public interface SysPermissionDao extends BaseJpaRepository<SysPermission> {

    /**
     * 根据角色列表查询权限列表
     */
    @Query(value = "SELECT DISTINCT sys_permission.* FROM sys_permission,sys_role,sys_role_permission WHERE sys_role.id = sys_role_permission.role_id AND sys_permission.id = sys_role_permission.permission_id AND sys_role.id IN (:ids)", nativeQuery = true)
    List<SysPermission> selectByRoleIdList(@Param("ids") List<String> ids);

}
