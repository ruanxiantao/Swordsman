package com.swordsman.common.sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author DuChao
 * @Date 2019-10-21 13:33
 * 阿里短信配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ali.sms")
public class AliSmsConfig {

    private String accessKeyId;

    private String accessKeySecret;

    /**
     * 客户端 Bean
     * @return
     */
    @Bean
    public IAcsClient client(){
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * 请求 bean
     * @return
     */
    @Bean
    public CommonRequest request(){
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);

        request.setDomain(AliSmsConstant.DOMAIN);
        request.setVersion(AliSmsConstant.VERSION);
        request.setAction(AliSmsConstant.ACTION);

        return request;
    }



}
