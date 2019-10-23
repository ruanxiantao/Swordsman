package com.swordsman.user.controller;

import cn.hutool.core.collection.CollUtil;
import com.swordsman.common.jpa.base.BaseJpaController;
import com.swordsman.common.web.ApiResult;
import com.swordsman.common.web.Status;
import com.swordsman.user.pojo.SysUser;
import com.swordsman.user.pojo.UserRole;
import com.swordsman.user.service.SysUserService;
import com.swordsman.user.service.UserRoleService;
import com.swordsman.user.util.SecurityUtil;
import com.swordsman.user.vo.AssignRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-22 15:39
 * SysUser Controller
 */
@Api(tags = "系统用户Api")
@RestController
@RequestMapping("/sysUser")
public class SysUserController extends BaseJpaController<SysUser, SysUserService> {

    @Autowired
    private UserRoleService userRoleService;

    @ApiOperation("给用户分配角色")
    @PostMapping("assignRoles")
    public ApiResult assignRoles(@Valid @RequestBody AssignRequest assignRequest) {

        List<UserRole> oldList = userRoleService.findByUserId(assignRequest.getAssignedId());
        userRoleService.deleteInBatch(oldList);

        String assignedId = assignRequest.getAssignedId();
        List<String> assignIds = assignRequest.getAssignIds();
        if (CollUtil.isEmpty(assignIds))
            return new ApiResult(Status.SUCCESS);
        else {
            List<UserRole> newList = new ArrayList<>();
            assignIds.forEach(assignId -> newList.add(new UserRole(assignedId, assignId)));
            userRoleService.save(newList);
            return new ApiResult(Status.SUCCESS);
        }

    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("getUserInfo")
    public ApiResult getUserInfo(){
        return new ApiResult(SecurityUtil.getCurrentUser());
    }


}
