package com.javaweb.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.javaweb.pojo.Result;
import com.javaweb.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override   // 目标资源运行前运行，返回true放行
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        System.out.println("preHandle");
        // 获取请求的url
        String url = req.getRequestURI().toString();
        log.info("请求的url：{}",url);
        // 判断请求url中是否包含login，包含的话为请求操作，放行
        if(url.contains("login")){
            log.info("登陆操作");
            return true;
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
            return false;
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
            return false;
        }
        // 合法放行
        log.info("令牌合法，放行");
        return true;
    }


    @Override   // 目标资源运行后运行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        System.out.println("postHandle");
    }

    @Override   // 视图渲染完毕后运行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        System.out.println("afterHandle");
    }
}
