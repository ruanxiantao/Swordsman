package com.swordsman.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author DuChao
 * @Date 2019-10-21 17:47
 * 自定义忽略路径配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "custom.config")
public class CustomConfig {
    /**
     * 不需要拦截的地址
     */
    private IgnoreConfig ignores;
}
