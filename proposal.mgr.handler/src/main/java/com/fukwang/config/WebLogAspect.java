package com.fukwang.config;

import com.fukwang.pojo.AdminInfo;
import com.fukwang.pojo.AdminLog;
import com.fukwang.service.AdminLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
@Component
public class WebLogAspect {
    @Resource
    private AdminLogService adminLogService;

    @Pointcut("execution(public * com.fukwang.handler..*.*(..))")
    public void controllerLog(){

    }

    @Before("controllerLog()")
    public void logBeforeController(JoinPoint joinPoint) {
        System.out.println("插入");
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        HttpSession session = request.getSession();
        AdminInfo admin = (AdminInfo) session.getAttribute("admin");
        if (admin != null && "post".equalsIgnoreCase(request.getMethod())) {
            System.out.println("插入日志");
            AdminLog log = new AdminLog();
            log.setAdminId(admin.getAdminId());
            log.setLogUrl(request.getRequestURI());
            Integer rs = adminLogService.insertLog(log);
        }
    }
}
