package com.swordsman.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:10
 * 日志切面
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 在所有 Controller 方法作增强方法
     */
    @Around("execution(* com.swordsman.*.controller.*.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {

        // 目标类名
        String targetName = pjp.getSignature().getDeclaringTypeName();

        // 目标方法名
        String methodName = pjp.getSignature().getName();

        /**
         * 记录方法入参
         */
        Object[] args = pjp.getArgs();
        Arrays.stream(args).forEach(arg -> log.info("\r\n arg : {}",arg));

        /**
         * 记录方法耗时
         */
        long startTime=System.currentTimeMillis();

        Object object = pjp.proceed();

        long endTime=System.currentTimeMillis();

        float excTime=(float)(endTime-startTime)/1000;
        log.info("\r\n【日志切面】:" + targetName + ":" + methodName + ": 耗时 :"+ excTime + "s");

        return object;
    }

}
