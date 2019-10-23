package com.swordsman.user.constants;

import java.util.Arrays;
import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 17:43
 * 常量类
 */
public interface Const {

    /**
     * 账号状态 : 启用
     */
    Integer USER_ENABLE = 1;

    /**
     * Jwt 在Redis 中存储前缀
     */
    String JWT_PREFIX = "Security_Jwt_";

    /**
     * 权限类型 : 按钮
     */
    Integer PERMISSION_BUTTON = 2;

    /**
     * JwtFilter 忽略路径
     */
    List<String> IGNORE_SECURITY =
            Arrays.asList( ".css",".js",".html","ui","v2","swagger","v3","png","error","ico","csrf","auth");

}
