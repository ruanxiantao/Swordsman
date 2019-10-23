package com.swordsman.common.config;

import com.google.common.base.Predicates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-23 12:39
 * Swagger 配置类
 */
@Configuration
@EnableSwagger2
@Slf4j
//@Profile("dev")
public class Swagger2Config {
    //获取swagger配置title
    @Value("${swagger.title:请设置配置}")
    private String title;
    //获取swagger配置description
    @Value("${swagger.description:请设置配置}")
    private String description;
    //获取swagger配置version
    @Value("${swagger.version:请设置配置}")
    private String version;

    @Bean
    public Docket webApiConfig() {

        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar.name("Authorization").description("认证token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); // header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());    // 根据每个方法名也知道当前方法在设置什么参数

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(webApiInfo())// 调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
                .select()//创建ApiSelectorBuilder对象
                .apis(RequestHandlerSelectors.basePackage("com.swordsman"))//扫描的包
                .paths(Predicates.not(PathSelectors.regex("/error.*")))//过滤掉错误路径
                .build()
                .globalOperationParameters(pars)
                .apiInfo(webApiInfo());
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .build();
    }

}
