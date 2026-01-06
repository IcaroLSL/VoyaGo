package com.voyago.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Autowired
    private DatabaseOperationInterceptor databaseOperationInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(databaseOperationInterceptor)
                .addPathPatterns("/v1/**")
                .addPathPatterns("/v2/**")
                .addPathPatterns("/v3/**");
    }
}
