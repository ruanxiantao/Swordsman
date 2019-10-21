package com.swordsman.common.exception;

/**
 * @Author DuChao
 * @Date 2019-08-27 16:56
 * 自定义异常
 */
public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
