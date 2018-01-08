package com.studiomediatech.contessa.store.none;

import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.store.Storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration("NONE")
public class Config implements Loggable {

    @Bean
    public Storage contessaNoneStorage() {

        return log_created(new NoneStorageImpl());
    }
}
