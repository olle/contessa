package com.studiomediatech.contessa.app.autoconfigure;

import com.studiomediatech.contessa.app.config.ContessaStorageConfigurationProperties;
import com.studiomediatech.contessa.app.config.ContessaUIConfigurationProperties;
import com.studiomediatech.contessa.contents.ContentsService;
import com.studiomediatech.contessa.contents.ContentsServiceImpl;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.storage.Storage;
import com.studiomediatech.contessa.storage.local.LocalStorageImpl;
import com.studiomediatech.contessa.storage.none.NoneStorageImpl;
import com.studiomediatech.contessa.storage.nosql.NoSqlStorageImpl;
import com.studiomediatech.contessa.storage.sql.DbStorageImpl;
import com.studiomediatech.contessa.ui.Handler;
import com.studiomediatech.contessa.ui.HandlerImpl;
import com.studiomediatech.contessa.ui.rest.Builder;
import com.studiomediatech.contessa.ui.rest.ContentUploadController;
import com.studiomediatech.contessa.ui.rest.Converter;

import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableConfigurationProperties(
    {
        ContessaStorageConfigurationProperties.class, // NOSONAR
        ContessaUIConfigurationProperties.class // NOSONAR
    }
)
public class ContessaAutoConfiguration {

    @Bean
    @ConditionalOnBean(Storage.class)
    @ConditionalOnMissingBean
    public ContentsService contessaContentsService(Storage storage) {

        return new ContentsServiceImpl(storage);
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.storage.type", havingValue = "NONE")
    @ComponentScan(basePackageClasses = NoneStorageImpl.class)
    public static class NoneStorageAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.storage.type", havingValue = "LOCAL")
    @ComponentScan(basePackageClasses = LocalStorageImpl.class)
    public static class LocalStorageAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.storage.type", havingValue = "NOSQL")
    @ComponentScan(basePackageClasses = NoSqlStorageImpl.class)
    @Import(
        {
            MongoAutoConfiguration.class, // NOSONAR
            MongoDataAutoConfiguration.class // NOSONAR
        }
    )
    public static class NoSqlStorageAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.storage.type", havingValue = "SQL")
    @ComponentScan(basePackageClasses = DbStorageImpl.class)
    @EnableJpaRepositories(basePackageClasses = DbStorageImpl.class)
    @EntityScan(basePackageClasses = DbStorageImpl.class)
    @Import(
        {
            DataSourceAutoConfiguration.class, // NOSONAR
            DataSourceTransactionManagerAutoConfiguration.class, // NOSONAR
            HibernateJpaAutoConfiguration.class // NOSONAR
        }
    )
    public static class SqlStorageAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty("contessa.ui.rest.enabled")
    @ComponentScan(basePackageClasses = ContentUploadController.class)
    public static class RestUiAutoConfiguration implements Loggable {

        @Bean
        @ConditionalOnMissingBean
        public Handler service(ContentsService contentsService) {

            return new HandlerImpl(contentsService);
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
    @Import(RabbitAutoConfiguration.class)
    public static class AmqpUiAutoConfiguration implements Loggable {

        // TODO
    }

    @Configuration
    @ConditionalOnProperty("contessa.ui.file.enabled")
    public static class FileUiAutoConfiguration implements Loggable {

        // TODO
    }
}
