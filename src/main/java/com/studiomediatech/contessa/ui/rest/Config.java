package com.studiomediatech.contessa.ui.rest;

import com.studiomediatech.contessa.contents.ContentsService;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.ui.Handler;
import com.studiomediatech.contessa.ui.HandlerImpl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration("rest")
public class Config implements Loggable {

    @Bean
    @ConditionalOnMissingBean
    public Handler service(ContentsService contentsService) {

        return log_created(new HandlerImpl(contentsService));
    }


    @Bean
    @ConditionalOnMissingBean
    public Converter restConverter() {

        return log_created(new Converter());
    }


    @Bean
    @ConditionalOnMissingBean
    public Builder restBuilder() {

        return log_created(new Builder());
    }
}
