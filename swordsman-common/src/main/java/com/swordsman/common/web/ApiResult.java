package com.swordsman.common.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.io.Serializable;

/**
 * @Author DuChao
 * @Date 2019-10-21 10:46
 * 统一返回结果
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult implements Serializable {

    private Object data;
    private Integer code;
    private String message;

    /**
     * 成功带返回值结果
     */
    public ApiResult(Object data) {
        this.code = 200;
        this.data = data;
    }

    /**
     * 枚举Code + Message
     */
    public ApiResult(Status status){
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    /**
     * 失败Code + Message
     */
    public ApiResult(String message) {
        this.code = 500;
        this.message = message;
    }
}
