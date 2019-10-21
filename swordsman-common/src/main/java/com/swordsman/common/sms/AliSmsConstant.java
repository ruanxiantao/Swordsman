package com.swordsman.common.sms;

/**
 * @Author DuChao
 * @Date 2019-10-21 13:28
 * 阿里短信常量
 */
public interface AliSmsConstant {

    /**
     * 写死配置
     */
    String DOMAIN = "dysmsapi.aliyuncs.com";

    /**
     * 写死配置
     */
    String VERSION = ("2017-05-25");

    /**
     * 写死配置
     */
    String ACTION = ("SendSms");

    /**
     * 短信验证码业务签名，自定义
     */
    String VALID_CODE_SIGN_NAME = "Swordsman";

    /**
     * 短信验证码模板 Code
     */
    String VALID_CODE_TEMPLATE_CODE = "";

}
