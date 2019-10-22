package com.swordsman.user.config;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.swordsman.common.exception.StatusException;
import com.swordsman.common.web.Status;
import com.swordsman.user.constants.Const;
import com.swordsman.user.dao.SysPermissionDao;
import com.swordsman.user.dao.SysRoleDao;
import com.swordsman.user.pojo.SysPermission;
import com.swordsman.user.pojo.SysRole;
import com.swordsman.user.vo.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author DuChao
 * @Date 2019-10-22 10:58
 * 动态路由认证
 */
@Component
public class RbacAuthorityService {

    @Autowired
    private SysRoleDao roleDao;

    @Autowired
    private SysPermissionDao permissionDao;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        checkRequest(request);
        Object userInfo = authentication.getPrincipal();
        boolean hasPermission = false;

        if (userInfo instanceof UserDetails){
            UserPrincipal principal = (UserPrincipal) userInfo;
            String userId = principal.getId();

            List<SysRole> roles = roleDao.selectByUserId(userId);
            List<String> roleIds = roles.stream()
                    .map(SysRole::getId)
                    .collect(Collectors.toList());
            List<SysPermission> permissions = permissionDao.selectByRoleIdList(roleIds);

            List<SysPermission> btnPerms = permissions.stream()
                    // 过滤页面权限
                    .filter(permission -> Objects.equals(permission.getType(), Const.PERMISSION_BUTTON))
                    // 过滤 Url 为空
                    .filter(permission -> StrUtil.isNotBlank(permission.getUrl()))
                    // 过滤 Method 为空
                    .filter(permission -> StrUtil.isNotBlank(permission.getMethod()))
                    .collect(Collectors.toList());

            for (SysPermission btnPerm : btnPerms) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(btnPerm.getUrl(),btnPerm.getMethod());
                if (matcher.matches(request)){
                    hasPermission = true;
                    break;
                }
            }

            return hasPermission;

        } else {
            return false;
        }

    }

    /**
     * 校验请求是否存在
     *
     * @param request
     */
    private void checkRequest(HttpServletRequest request) {
        String method = request.getMethod();
        Multimap<String, String> urlMapping = allUrlMapping();
        for (String uri : urlMapping.keySet()) {
            // 通过 AntPathRequestMatcher 匹配 Url
            // 可以通过 2 种方式创建 AntPathRequestMatcher
            // 1: uri + method，可以直接判断方法是否匹配，此处因为我们自定义抛出方法不匹配的异常，所以用第二种
            // 2. uri，不校验请求方法，只校验请求路径
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(uri);
            if (matcher.matches(request)) {
                if (!urlMapping.get(uri).contains(method))
                    throw new StatusException(Status.HTTP_BAD_METHOD);
                else
                    return;
            }
        }
        throw new StatusException(Status.REQUEST_NOT_FOUND);
    }

    /**
     * 获取所有 Url Mapping，返回格式为{"/test":["GET","POST"],"/sys":["GET","DELETE"]}
     *
     * @return
     */
    private Multimap<String, String> allUrlMapping() {
        Multimap<String, String> urlMapping = ArrayListMultimap.create();

        // 获取url 与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();

        handlerMethods.forEach((k, v) -> {
            // 获取当前 key 下的所有Url
            Set<String> url = k.getPatternsCondition()
                    .getPatterns();
            RequestMethodsRequestCondition method = k.getMethodsCondition();

            // 为每个 URL 添加所有的请求方法
            url.forEach(s -> urlMapping.putAll(s, method.getMethods()
                    .stream()
                    .map(Enum::toString)
                    .collect(Collectors.toList())));
        });

        return urlMapping;
    }

}
