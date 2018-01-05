package com.studiomediatech.contessa.ui.web;

import com.studiomediatech.contessa.validation.ValidationService;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration("web")
public class Config {

    @Bean
    @ConditionalOnMissingBean
    public WebValidator webValidator(ValidationService validationService) {

        return new WebValidatorImpl(validationService);
    }


    @Bean
    @ConditionalOnMissingBean
    public WebConverter webConverter() {

        return new WebConverterImpl();
    }
}
