package com.swordsman.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author DuChao
 * @Date 2019-08-27 16:56
 * 自定义异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
