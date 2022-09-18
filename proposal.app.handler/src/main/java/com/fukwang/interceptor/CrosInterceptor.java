package com.fukwang.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CrosInterceptor  implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.所有的请求我都允许跨域
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        // 2.所有的请求方式我都允许跨域（get, post...）
        response.setHeader("Access-Control-Allow-Methods", "*");
        // 3.最大的保留时间（缓存）
        response.setHeader("Access-Control-Max-Age", "3600");
        // 4.设置允许访问的请求头参数（浏览器自带的配置参数）信息
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept");
        //保持跨域时的 Cookie同步
        response.setHeader("Access-Control-Allow-Credentials","true");
        return true;
    }
}
