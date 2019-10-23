package com.swordsman.user.service;

import com.swordsman.common.jpa.base.BaseJpaService;
import com.swordsman.user.dao.RolePermissionDao;
import com.swordsman.user.pojo.RolePermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-23 14:13
 * RolePermission Service
 */
@Service
@Transactional
@Slf4j
public class RolePermissionService extends BaseJpaService<RolePermission, RolePermissionDao> {

    public List<RolePermission> findByRoleId(String roleId){
        return baseDao.findByRoleId(roleId);
    }

}
