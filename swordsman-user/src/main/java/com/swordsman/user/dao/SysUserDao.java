package com.swordsman.user.dao;

import com.swordsman.common.jpa.base.BaseJpaRepository;
import com.swordsman.user.pojo.SysUser;

import java.util.Optional;

/**
 * @Author DuChao
 * @Date 2019-10-21 17:09
 * SysUser Dao
 */
public interface SysUserDao extends BaseJpaRepository<SysUser> {

    Optional<SysUser> findByUsernameOrEmailOrPhone(String username, String email, String phone);

}
