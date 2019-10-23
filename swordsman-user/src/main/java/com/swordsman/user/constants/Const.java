package com.swordsman.user.constants;

import java.util.Arrays;
import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 17:43
 * 常量类
 */
public interface Const {

    Integer USER_ENABLE = 1;

    String JWT_PREFIX = "Security_Jwt_";

    Integer PERMISSION_BUTTON = 2;

    List<String> IGNORE_SECURITY =
            Arrays.asList( ".css",".js",".html","ui","v2","swagger","v3","png","error","ico","csrf","auth");

    String ANONYMOUS_NAME = "匿名用户";

}
