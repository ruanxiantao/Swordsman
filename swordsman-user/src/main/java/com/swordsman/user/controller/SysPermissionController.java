package com.swordsman.user.controller;

import com.swordsman.common.jpa.base.BaseJpaController;
import com.swordsman.user.pojo.SysPermission;
import com.swordsman.user.service.SysPermissionService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author DuChao
 * @Date 2019-10-23 14:15
 * SysPermission Controller
 */
@Api(tags = "系统权限Api")
@RestController
@RequestMapping("/sysPermission")
public class SysPermissionController extends BaseJpaController<SysPermission, SysPermissionService> {
}
