package com.studiomediatech.contessa.store.files;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Configuration;


@Configuration("LOCAL")
@EnableConfigurationProperties(ContessaFilesProperties.class)
public class Config {

    // OK
}
