package com.swordsman.user.config;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 17:46
 * 忽略的请求路径配置
 */
@Data
public class IgnoreConfig {
    /**
     * 需要忽略的 URL 格式，不考虑请求方法
     */
    private List<String> pattern = Lists.newArrayList();

    /**
     * 需要忽略的 GET 请求
     */
    private List<String> get = Lists.newArrayList();

    /**
     * 需要忽略的 POST 请求
     */
    private List<String> post = Lists.newArrayList();

    /**
     * 需要忽略的 DELETE 请求
     */
    private List<String> delete = Lists.newArrayList();

    /**
     * 需要忽略的 PUT 请求
     */
    private List<String> put = Lists.newArrayList();

    /**
     * 需要忽略的 OPTIONS 请求
     */
    private List<String> options = Lists.newArrayList();

}
