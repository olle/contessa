package com.studiomediatech.content.store;

import com.studiomediatech.content.store.web.RequestUrlInterceptor;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Application starter.
 */
@SpringBootApplication
public class ContentServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(ContentServiceApplication.class, args);
    }

    /**
     * Custom web MVC configuration.
     */
    @Configuration
    public static class WebConfig extends WebMvcConfigurerAdapter {

        @Autowired
        private RequestUrlInterceptor requestUrlInterceptor;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {

            registry.addInterceptor(requestUrlInterceptor).addPathPatterns("/**");
        }
    }
}
