package com.studiomediatech.contessa.ui.rest;

import com.studiomediatech.contessa.contents.ContentsService;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.ui.UiHandler;
import com.studiomediatech.contessa.ui.UiHandlerImpl;
import com.studiomediatech.contessa.validation.ValidationService;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration("rest")
public class Config implements Loggable {

    @Bean
    @ConditionalOnMissingBean
    public RestValidator restValidator(ValidationService validationService) {

        return log_created(new RestValidatorImpl(validationService));
    }


    @Bean
    @ConditionalOnMissingBean
    public RestConverter restConverter() {

        return log_created(new RestConverterImpl());
    }


    @Bean
    @ConditionalOnMissingBean
    public UiHandler service(ContentsService contentsService) {

        return log_created(new UiHandlerImpl(contentsService));
    }
}
