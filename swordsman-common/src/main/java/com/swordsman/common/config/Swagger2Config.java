package com.swordsman.common.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author DuChao
 * @Date 2019-10-21 13:52
 * Swagger2 接口文档配置
 */
@Configuration
@EnableSwagger2
//@Profile("dev") 只在 dev 环境下启用该配置
public class Swagger2Config {

    @Value("${swagger.title:请设置配置}")
    private String title;

    @Value("${swagger.description:请设置配置}")
    private String description;

    @Value("${swagger.version:请设置配置}")
    private String version;

    @Bean
    public Docket webApiConfig() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(webApiInfo())  // 调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
                    .select()   // 创建ApiSelectorBuilder对象
                    .apis(RequestHandlerSelectors.basePackage("com.swordsman.*.controller"))    // 扫描的包
                    .paths(Predicates.not(PathSelectors.regex("/error.*"))) // 过滤掉错误路径
                    .build();
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .build();
    }

}
