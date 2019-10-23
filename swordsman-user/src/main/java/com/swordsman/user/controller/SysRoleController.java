package com.swordsman.user.controller;

import cn.hutool.core.collection.CollUtil;
import com.swordsman.common.jpa.base.BaseJpaController;
import com.swordsman.common.web.ApiResult;
import com.swordsman.common.web.Status;
import com.swordsman.user.pojo.RolePermission;
import com.swordsman.user.pojo.SysRole;
import com.swordsman.user.service.RolePermissionService;
import com.swordsman.user.service.SysRoleService;
import com.swordsman.user.vo.AssignRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-23 14:14
 * SysRole Controller
 */
@Api(tags = "系统角色Api")
@RestController
@RequestMapping("/sysRole")
public class SysRoleController extends BaseJpaController<SysRole, SysRoleService> {

    @Autowired
    private RolePermissionService rolePermissionService;

    @ApiOperation("给角色分配权限")
    @PostMapping("assignPermissions")
    public ApiResult assignRoles(@Valid @RequestBody AssignRequest assignRequest) {

        List<RolePermission> oldList = rolePermissionService.findByRoleId(assignRequest.getAssignedId());
        rolePermissionService.deleteInBatch(oldList);

        String assignedId = assignRequest.getAssignedId();
        List<String> assignIds = assignRequest.getAssignIds();
        if (CollUtil.isEmpty(assignIds))
            return new ApiResult(Status.SUCCESS);
        else {
            List<RolePermission> newList = new ArrayList<>();
            assignIds.forEach(assignId -> newList.add(new RolePermission(assignedId, assignId)));
            rolePermissionService.save(newList);
            return new ApiResult(Status.SUCCESS);
        }

    }

}
