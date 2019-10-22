package com.swordsman.user.service;

import com.swordsman.common.jpa.base.BaseJpaService;
import com.swordsman.user.dao.SysUserDao;
import com.swordsman.user.pojo.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Author DuChao
 * @Date 2019-10-22 15:38
 * SysUser Service
 */
@Service
@Transactional
@Slf4j
public class SysUserService extends BaseJpaService<SysUser, SysUserDao> {
}
