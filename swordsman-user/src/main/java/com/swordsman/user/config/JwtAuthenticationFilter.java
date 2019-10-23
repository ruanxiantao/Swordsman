package com.swordsman.user.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;
import com.swordsman.common.exception.StatusException;
import com.swordsman.common.web.Status;
import com.swordsman.user.constants.Const;
import com.swordsman.user.service.CustomUserDetailsService;
import com.swordsman.user.util.JwtUtil;
import com.swordsman.user.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

/**
 * @Author DuChao
 * @Date 2019-10-22 09:43
 * JWT 认证过滤器
 * 继承 OncePreRequestFilter : 在请求到达之前执行一次过滤器
 */
@Slf4j
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomConfig customConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String servletPath = request.getServletPath();
        boolean ignore = false;
        for (String s : Const.IGNORE_SECURITY) {
            if (servletPath.contains(s)){
                ignore = true;
                break;
            }
        }

        if (Objects.equals(servletPath,"/"))
            ignore = true;

        if (ignore){
            filterChain.doFilter(request, response);
            return;
        }

        if (checkIgnores(request)){
            filterChain.doFilter(request,response);
            return;
        }

        String jwt = jwtUtil.getJwtFromRequest(request);

        if (StrUtil.isNotBlank(jwt)){
            String username = jwtUtil.getUsernameFromJwt(jwt);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(token);

            filterChain.doFilter(request,response);
        } else {
            log.info("被过滤拦截的路径是 : {}",servletPath);
            ResponseUtil.renderJson(response, Status.UNAUTHORIZED);
        }

    }

    /**
     * 请求是否不需要进行权限拦截
     *
     * @param request 当前请求
     * @return true : 忽略 ，false : 不忽略
     */
    private boolean checkIgnores(HttpServletRequest request) {
        String method = request.getMethod();
        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (Objects.isNull(httpMethod))
            httpMethod = httpMethod.GET;

        Set<String> ignores = Sets.newHashSet();

        switch (httpMethod) {
            case GET:
                ignores.addAll(customConfig.getIgnores()
                        .getGet());
                break;
            case PUT:
                ignores.addAll(customConfig.getIgnores()
                        .getPut());
                break;
            case POST:
                ignores.addAll(customConfig.getIgnores()
                        .getPost());
                break;
            case DELETE:
                ignores.addAll(customConfig.getIgnores()
                        .getDelete());
                break;
            default:
                break;
        }

        ignores.addAll(customConfig.getIgnores().getPattern());
        if (CollUtil.isNotEmpty(ignores)){
            for (String ignore : ignores) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore,method);
                if (matcher.matches(request))
                    return true;
            }
        }

        return false;
    }
}
