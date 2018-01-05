package com.example.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.app.core.appcontroller.AuthenticationInterceptor;


@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {      
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
        .addResourceLocations("classpath:/static/")
        .setCachePeriod(0);
    }
    
    @Bean
    public AuthenticationInterceptor getAuthenticationInterceptor() {
    	return new AuthenticationInterceptor();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(getAuthenticationInterceptor());
    }
}
