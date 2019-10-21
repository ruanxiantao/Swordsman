package com.swordsman.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author DuChao
 * @Date 2019-10-21 11:22
 * 自定义手机号校验注解
 */
@Pattern(regexp = "1[3|4|5|7|8][0-9]\\d{8}")
@NotEmpty
@Target({ METHOD, FIELD, CONSTRUCTOR, PARAMETER })
@Constraint(validatedBy = {})
@Retention(RUNTIME)
public @interface Phone {

    String message() default "手机号校验错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
