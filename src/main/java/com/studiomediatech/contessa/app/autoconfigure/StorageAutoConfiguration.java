package com.studiomediatech.contessa.app.autoconfigure;

import com.studiomediatech.contessa.app.config.ContessaStorageConfiguration;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.storage.Storage;
import com.studiomediatech.contessa.storage.local.LocalStorageImpl;
import com.studiomediatech.contessa.storage.none.NoneStorageImpl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class StorageAutoConfiguration {

    @Configuration
    @ConditionalOnProperty(name = "contessa.storage.type", havingValue = "NONE")
    public static class NoneStorageAutoConfiguration implements Loggable {

        @Bean
        @ConditionalOnMissingBean
        public Storage noneStorage() {

            return new NoneStorageImpl();
        }
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.storage.type", havingValue = "LOCAL")
    public static class LocalStorageAutoConfiguraiton implements Loggable {

        public Storage localStorage(ContessaStorageConfiguration config) {

            return new LocalStorageImpl(config);
        }
    }
}
