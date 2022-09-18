package com.fukwang.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorPageConfig implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        System.out.println("开始配置错误页面");
        ErrorPage page404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error404");
        ErrorPage page500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error500");
        registry.addErrorPages(page404, page500);
    }
}
