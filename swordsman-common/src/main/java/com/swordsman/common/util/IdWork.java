package com.swordsman.common.util;

import cn.hutool.core.lang.Snowflake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author DuChao
 * @Date 2019-10-21 13:01
 * 雪花 ID 生成器
 */
@Configuration
public class IdWork {

    @Bean
    public Snowflake snowflake(){
        return new Snowflake(1,1);
    }

}
