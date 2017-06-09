package com.studiomediatech.contessa.app.autoconfigure;

import com.studiomediatech.contessa.app.config.ContessaStorageConfigurationProperties;
import com.studiomediatech.contessa.app.config.ContessaUIConfigurationProperties;
import com.studiomediatech.contessa.contents.ContentsService;
import com.studiomediatech.contessa.contents.ContentsServiceImpl;
import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.storage.Storage;
import com.studiomediatech.contessa.storage.db.DbEntry;
import com.studiomediatech.contessa.storage.db.DbEntryRepository;
import com.studiomediatech.contessa.storage.db.DbEntryToEntryConverter;
import com.studiomediatech.contessa.storage.db.DbStorageImpl;
import com.studiomediatech.contessa.storage.db.EntryToDbEntryConverter;
import com.studiomediatech.contessa.storage.local.LocalStorageImpl;
import com.studiomediatech.contessa.storage.none.NoneStorageImpl;
import com.studiomediatech.contessa.ui.Service;
import com.studiomediatech.contessa.ui.rest.Builder;
import com.studiomediatech.contessa.ui.rest.ContentUploadController;
import com.studiomediatech.contessa.ui.rest.Converter;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(
    { ContessaStorageConfigurationProperties.class, ContessaUIConfigurationProperties.class }
)
public class ContessaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ContentsService contentsService(Storage storage) {

        return new ContentsServiceImpl(storage);
    }

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

        public Storage localStorage(ContessaStorageConfigurationProperties config) {

            return new LocalStorageImpl(config);
        }
    }

    @Configuration
    @AutoConfigureAfter({ ContessaAutoConfiguration.class })
    @ConditionalOnProperty(name = "contessa.storage.type", havingValue = "SQL")
    @ComponentScan(basePackageClasses = DbEntryRepository.class)
    public static class SqlStorageAutoConfiguraiton implements Loggable {

        @Bean
        @ConditionalOnMissingBean
        public org.springframework.core.convert.converter.Converter<DbEntry, Entry> decooder() {

            return new DbEntryToEntryConverter();
        }


        @Bean
        @ConditionalOnMissingBean
        public org.springframework.core.convert.converter.Converter<Entry, DbEntry> encoder() {

            return new EntryToDbEntryConverter();
        }


        @Bean
        @ConditionalOnMissingBean
        public Storage sqlStorage(ContessaStorageConfigurationProperties config,
            org.springframework.core.convert.converter.Converter<DbEntry, Entry> decoder,
            org.springframework.core.convert.converter.Converter<Entry, DbEntry> encoder, DbEntryRepository repo) {

            return new DbStorageImpl(decoder, encoder, repo);
        }
    }

    @Configuration
    @ConditionalOnProperty("contessa.ui.rest.enabled")
    @ComponentScan(basePackageClasses = ContentUploadController.class)
    public static class RestUiAutoConfiguration implements Loggable {

        @Bean
        @ConditionalOnMissingBean
        public Service service(ContentsService contentsService) {

            return new Service(contentsService);
        }


        @Bean
        @ConditionalOnMissingBean
        public Converter restConverter() {

            return new Converter();
        }


        @Bean
        @ConditionalOnMissingBean
        public Builder restBuilder() {

            return new Builder();
        }
    }

    @Configuration
    @ConditionalOnProperty("contessa.ui.amqp.enabled")
    public static class AmqpUiAutoConfiguration implements Loggable {

        // TODO
    }

    @Configuration
    @ConditionalOnProperty("contessa.ui.file.enabled")
    public static class FileUiAutoConfiguration implements Loggable {

        // TODO
    }
}
