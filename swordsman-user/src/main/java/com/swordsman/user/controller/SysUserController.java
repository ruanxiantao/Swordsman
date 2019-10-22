package com.swordsman.user.controller;

import com.swordsman.common.jpa.base.BaseJpaController;
import com.swordsman.user.pojo.SysUser;
import com.swordsman.user.service.SysUserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author DuChao
 * @Date 2019-10-22 15:39
 * SysUser Controller
 */
@Api(tags = "系统用户Api")
@RestController
@RequestMapping("/sysUser")
public class SysUserController extends BaseJpaController<SysUser, SysUserService> {
}
