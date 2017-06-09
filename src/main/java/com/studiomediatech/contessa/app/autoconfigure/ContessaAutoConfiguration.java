package com.studiomediatech.contessa.app.autoconfigure;

import com.studiomediatech.contessa.contents.ContentsService;
import com.studiomediatech.contessa.contents.ContentsServiceImpl;
import com.studiomediatech.contessa.storage.Storage;
import com.studiomediatech.contessa.ui.rest.ContentUploadController;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ContessaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ContentsService contentsService(Storage storage) {

        return new ContentsServiceImpl(storage);
    }
}
