package com.studiomediatech.content.store;

import com.studiomediatech.content.store.web.RequestUrlInterceptor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
public class ContentServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(ContentServiceApplication.class, args);
    }

    @Configuration
    public static class WebConfig extends WebMvcConfigurerAdapter {

        @Bean
        public RequestUrlInterceptor requestUrlInterceptor() {

            return new RequestUrlInterceptor();
        }


        @Override
        public void addInterceptors(InterceptorRegistry registry) {

            registry.addInterceptor(requestUrlInterceptor()).addPathPatterns("/**");
        }
    }
}
