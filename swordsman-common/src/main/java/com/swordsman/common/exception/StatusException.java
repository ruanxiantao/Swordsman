package com.swordsman.common.exception;

import com.swordsman.common.web.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author DuChao
 * @Date 2019-10-22 10:13
 * 状态枚举异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StatusException extends RuntimeException{

    private Status status;

    public StatusException(Status status){
        super(status.getMessage());
        this.status = status;
    }

}
