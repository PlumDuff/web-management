package com.javaweb.filter;

import com.alibaba.fastjson.JSONObject;
import com.javaweb.pojo.Result;
import com.javaweb.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")
public class LoginCheckerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        // 获取请求的url
        String url = req.getRequestURI().toString();
        log.info("请求的url：{}",url);
        // 判断请求url中是否包含login，包含的话为请求操作，放行
        if(url.contains("login")){
            log.info("登陆操作");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //获取请求头的令牌
        String jwt = req.getHeader("token");

        // 判断令牌是否存在
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头token为空，返回未登陆的信息");
            Result error = Result.error("NOT_LOGIN");
            // 将对象转换为json格式
            String notLogin = JSONObject.toJSONString(error);
            res.getWriter().write(notLogin);
            return ;
        }

        // 校验
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
           e.printStackTrace();
           log.info("解析令牌失败！");
            Result error = Result.error("NOT_LOGIN");
            // 将对象转换为json格式
            String notLogin = JSONObject.toJSONString(error);
            res.getWriter().write(notLogin);
            return ;
        }
        // 合法放行
        log.info("令牌合法，放行");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
