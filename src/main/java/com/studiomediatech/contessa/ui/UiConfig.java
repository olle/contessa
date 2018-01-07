package com.studiomediatech.contessa.ui;

import com.studiomediatech.contessa.contents.ContentsService;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.validation.ValidationService;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration("ui")
public class UiConfig implements Loggable {

    @Bean
    @ConditionalOnMissingBean
    public HttpValidator httpValidator(ValidationService validationService) {

        return new HttpValidatorImpl(validationService);
    }


    @Bean
    @ConditionalOnMissingBean
    public UiHandler service(ContentsService contentsService) {

        return log_created(new UiHandlerImpl(contentsService));
    }
}
