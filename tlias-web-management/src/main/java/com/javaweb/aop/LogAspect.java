package com.javaweb.aop;


import com.alibaba.fastjson.JSONObject;
import com.javaweb.mapper.OperateLogMapper;
import com.javaweb.pojo.OperateLog;
import com.javaweb.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class LogAspect {
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(com.javaweb.anno.Log)")
    public Object recordLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //操作人ID
        // 获取请求头中 的jwt令牌，解析令牌
        String jwt = httpServletRequest.getHeader("token");
        Claims claims = JwtUtils.parseJWT(jwt);
        Integer  operateUser = (Integer) claims.get("id");
        //操作时间
        LocalDateTime now = LocalDateTime.now();
        //操作类名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        //操作方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        //操作方法参数
        Object[] args = proceedingJoinPoint.getArgs();
        String methodParams = Arrays.toString(args);

        // 调用原始目标方法
        Long begin = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        Long end = System.currentTimeMillis();
        //操作方法返回值
        String returnValue = JSONObject.toJSONString(result);
        //操作耗时
        Long costTime = end-begin;

        // 记录操作日志
        OperateLog operateLog = new OperateLog(null,operateUser,now,className,methodName,methodParams,returnValue,costTime);
        operateLogMapper.insert(operateLog);
        log.info("AOP记录操作日志：{}",operateLog);
        return result;
    }
}
