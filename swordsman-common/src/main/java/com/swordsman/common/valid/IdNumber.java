package com.swordsman.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author DuChao
 * @Date 2019-10-21 11:21
 * 自定义身份证号校验注解
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdNumberValidator.class)
public @interface IdNumber {

    String message() default "身份证号校验失败";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
