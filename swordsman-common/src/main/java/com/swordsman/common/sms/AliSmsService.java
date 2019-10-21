package com.swordsman.common.sms;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.swordsman.common.redis.RedisConstant;
import com.swordsman.common.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author DuChao
 * @Date 2019-10-21 13:40
 * 阿里短信服务
 * 使用同一个签名，对同一个手机号码发送短信验证码，支持1条/分钟，5条/小时，10条/天
 */
@Slf4j
@Service
public class AliSmsService {

    @Autowired
    private IAcsClient client;

    @Autowired
    private CommonRequest request;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据手机号发送验证码
     * 不同的业务场景通用，例: 注册、手机号登陆、找回密码 ...
     */
    @Async
    public void validCodeSms(String phone) {

        String code = jsonCode();

        request.putQueryParameter("SignName", AliSmsConstant.VALID_CODE_SIGN_NAME);
        request.putQueryParameter("TemplateCode", AliSmsConstant.VALID_CODE_TEMPLATE_CODE);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("TemplateParam", code);

        log.info("\r\n【短信发送】参数 : {} , {}", phone, code);

        // 验证码存入 redis
        redisUtil.setEx(RedisConstant.SMS_PREFIX + phone,code);

        try {
            client.getCommonResponse(request);
        } catch (ClientException e) {
            log.error("\r\n【短信发送异常】 : {}", e.getMessage());
        }

    }

    /**
     * 短信验证码生成
     */
    public static String jsonCode() {

        JSONObject code = new JSONObject();
        code.put("code", RandomUtil.randomNumbers(4));
        return code.toString();
    }

}
