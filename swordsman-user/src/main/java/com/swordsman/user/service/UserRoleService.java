package com.swordsman.user.service;

import com.swordsman.common.jpa.base.BaseJpaService;
import com.swordsman.user.dao.UserRoleDao;
import com.swordsman.user.pojo.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-23 14:13
 * UserRole Service
 */
@Service
@Transactional
@Slf4j
public class UserRoleService extends BaseJpaService<UserRole, UserRoleDao> {

    public List<UserRole> findByUserId(String userId){
        return baseDao.findByUserId(userId);
    }

}
