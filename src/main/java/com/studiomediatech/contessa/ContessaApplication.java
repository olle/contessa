package com.studiomediatech.contessa;

import com.studiomediatech.contessa.contents.ContentsService;
import com.studiomediatech.contessa.contents.ContentsServiceImpl;
import com.studiomediatech.contessa.http.RequestUrlInterceptor;
import com.studiomediatech.contessa.storage.ContentsBackend;
import com.studiomediatech.contessa.storage.ContentsBackendNullImpl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Application starter.
 */
@SpringBootApplication
public class ContessaApplication {

    public static void main(String[] args) {

        SpringApplication.run(ContessaApplication.class, args);
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

    @Configuration
    public static class ServiceConfig {

        /*
         * Always wired, unconditionally since this is a part of the internal DI setup.
         */
        @Bean
        public ContentsService contentsService(ContentsBackend contentsBackend) {

            return new ContentsServiceImpl(contentsBackend);
        }
    }

    @Configuration
    public static class BackendConfig implements Loggable {

        /*
         * This is just a fallback for the case that the backend is missing, and in that
         * case we also warn the user that no "real" storage backend could be found.
         */
        @Bean
        @ConditionalOnMissingBean
        public ContentsBackend contentsBackend() {

            logger().warn("No other backend found, using a stub/null/logging storage backend only.");

            return new ContentsBackendNullImpl();
        }
    }
}
