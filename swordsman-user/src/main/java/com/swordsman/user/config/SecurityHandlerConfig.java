package com.swordsman.user.config;

import com.swordsman.common.web.Status;
import com.swordsman.user.util.ResponseUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @Author DuChao
 * @Date 2019-10-22 13:29
 * Security 认证失败处理
 */
@Configuration
public class SecurityHandlerConfig {

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return ((request, response, accessDeniedException) -> ResponseUtil.renderJson(response,Status.ACCESS_DENIED));
    }
}
