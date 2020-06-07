package com.wl.sec_kill.web;

import com.wl.sec_kill.controller.SecKillController;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    SecKillController.AntiRefreshInterceptor antiRefreshInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(antiRefreshInterceptor).addPathPatterns(new String[]{"/goods" , "/gid/" , "/abc/**"});

    }
}