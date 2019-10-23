package com.swordsman.user.service;

import com.swordsman.common.jpa.base.BaseJpaService;
import com.swordsman.user.dao.SysRoleDao;
import com.swordsman.user.pojo.SysRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author DuChao
 * @Date 2019-10-23 14:11
 * SysRole Service
 */
@Service
@Transactional
@Slf4j
public class SysRoleService extends BaseJpaService<SysRole, SysRoleDao> {
}
