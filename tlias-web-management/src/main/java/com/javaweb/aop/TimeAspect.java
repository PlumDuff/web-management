package com.javaweb.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Slf4j
//@Component
//Aspect // 声明为AOP类
public class TimeAspect {

    @Around("execution(* com.javaweb.service.*.*(..))")     // 运行service层所有方法或接口时都会运行该方法封装的代码
    public Object recordTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        // 记录开始时间
        long begin = System.currentTimeMillis();
        // 调用原始方法
        Object result= proceedingJoinPoint.proceed();
        // 记录结束时间
        long end  = System.currentTimeMillis();
        log.info(proceedingJoinPoint.getSignature()+"方法执行耗时{}ms",end-begin);
        return result;
    }
}
