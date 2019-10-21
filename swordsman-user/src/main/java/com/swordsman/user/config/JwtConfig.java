package com.swordsman.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author DuChao
 * @Date 2019-10-21 17:48
 * JWT 配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt.config")
public class JwtConfig {
    /**
     * jwt 加密 key，默认值：swordsman.
     */
    private String key = "swordsman";

    /**
     * jwt 过期时间，默认值：600000 {@code 10 分钟}.
     */
    private Long ttl = 600000L;

    /**
     * 开启 记住我 之后 jwt 过期时间，默认值 604800000 {@code 7 天}
     */
    private Long remember = 604800000L;
}
