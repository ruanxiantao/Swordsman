package com.swordsman.user.util;

import cn.hutool.json.JSONUtil;
import com.swordsman.common.web.ApiResult;
import com.swordsman.common.web.Status;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author DuChao
 * @Date 2019-10-22 13:35
 * Response 通用工具类
 */
@Slf4j
public class ResponseUtil {

    /**
     * 往 response 写出 json
     *
     * @param response 响应
     * @param status   状态
     */
    public static void renderJson(HttpServletResponse response, Status status) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json;charset=UTF-8");
            ApiResult apiResult = new ApiResult(status);

            response.getWriter()
                    .write(JSONUtil.toJsonStr(apiResult));
        } catch (IOException e) {
            log.error("Response写出JSON异常，", e);
        }
    }

}