package com.swordsman.user.vo;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swordsman.user.constants.Const;
import com.swordsman.user.pojo.SysPermission;
import com.swordsman.user.pojo.SysRole;
import com.swordsman.user.pojo.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author DuChao
 * @Date 2019-10-21 17:33
 * 自定义 UserDetails
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    @ApiModelProperty(value = "主键Id")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    @JsonIgnore
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "性别 1 男 | 2 女")
    private Integer sex;

    @ApiModelProperty(value = "状态 1 启用 | 2 禁用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "用户角色列表")
    private List<String> roles;

    @ApiModelProperty(value = "用户权限列表")
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(SysUser user, List<SysRole> roles, List<SysPermission> permissions) {
        List<String> roleNames = roles.stream()
                .map(SysRole::getName)
                .collect(Collectors.toList());

        List<GrantedAuthority> authorities = permissions.stream()
                .filter(permission -> StrUtil.isNotBlank(permission.getPermission()))
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getNickname(), user.getPhone(), user.getEmail(), user.getSex(), user.getStatus(), user.getCreateTime(), user.getUpdateTime(), roleNames, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Const.USER_ENABLE == status;
    }
}
