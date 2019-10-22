package com.swordsman.user.service;

import com.swordsman.common.exception.CustomException;
import com.swordsman.user.dao.SysPermissionDao;
import com.swordsman.user.dao.SysRoleDao;
import com.swordsman.user.dao.SysUserDao;
import com.swordsman.user.pojo.SysPermission;
import com.swordsman.user.pojo.SysRole;
import com.swordsman.user.pojo.SysUser;
import com.swordsman.user.vo.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author DuChao
 * @Date 2019-10-21 17:21
 * 自定义Security 认证服务
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysPermissionDao sysPermissionDao;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmailOrPhone) throws UsernameNotFoundException {

        SysUser user = sysUserDao.findByUsernameOrEmailOrPhone(usernameOrEmailOrPhone, usernameOrEmailOrPhone, usernameOrEmailOrPhone)
                .orElseThrow(() -> new CustomException("未找到用户信息 : " + usernameOrEmailOrPhone));

        List<SysRole> roles = sysRoleDao.selectByUserId(user.getId());
        List<String> roleIds = roles.stream()
                .map(SysRole::getId)
                .collect(Collectors.toList());

        List<SysPermission> permissions = sysPermissionDao.selectByRoleIdList(roleIds);

        return UserPrincipal.create(user,roles,permissions);
    }

}
