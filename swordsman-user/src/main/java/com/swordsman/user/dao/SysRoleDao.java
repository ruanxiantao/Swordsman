package com.swordsman.user.dao;

import com.swordsman.common.jpa.base.BaseJpaRepository;
import com.swordsman.user.pojo.SysRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 17:11
 * SysRole Dao
 */
public interface SysRoleDao extends BaseJpaRepository<SysRole> {

    /**
     * 根据用户id 查询角色列表
     */
    @Query(value = "SELECT sys_role.* FROM sys_role,sys_user,sys_user_role WHERE sys_user.id = sys_user_role.user_id AND sys_role.id = sys_user_role.role_id AND sys_user.id = :userId", nativeQuery = true)
    List<SysRole> selectByUserId(@Param("userId") String userId);

}
