package com.fukwang.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fukwang.dto.RouteDto;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HtmlInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("网址" + request.getRequestURI());
        if(request.getAttribute("token") == null) {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("UTF-8");
            RouteDto dto = new RouteDto();
            System.out.println("error");
            dto.setMessage("Error...");
            response.getWriter().println(JSONObject.toJSONString(dto));
            return false;
        }
        System.out.println("可以返回");
        return true; // 这里如果写false前端就收不到任何数据！！！
    }
}
