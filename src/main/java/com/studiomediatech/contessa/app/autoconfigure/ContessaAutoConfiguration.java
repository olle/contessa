package com.studiomediatech.contessa.app.autoconfigure;

import com.studiomediatech.contessa.contents.ContentsService;
import com.studiomediatech.contessa.contents.ContentsServiceImpl;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.storage.Storage;
import com.studiomediatech.contessa.storage.local.ContessaLocal;
import com.studiomediatech.contessa.storage.none.ContessaNone;
import com.studiomediatech.contessa.storage.nosql.ContessaNoSql;
import com.studiomediatech.contessa.storage.sql.ContessaSql;
import com.studiomediatech.contessa.ui.amqp.ContessaAmqp;
import com.studiomediatech.contessa.ui.rest.ContessaRest;
import com.studiomediatech.contessa.ui.web.ContessaWeb;
import com.studiomediatech.contessa.validation.ValidationService;
import com.studiomediatech.contessa.validation.ValidationServiceImpl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * Auto-configuration for Contessa, establishes the configurable nature of the application - to be able to choose a
 * storage backend as well as toggling user interfaces on/off.
 *
 * <p>The basic structure here is that the conditionals will enable component scanning in the module-packages, and
 * activate each of the more specific configurations. Just follow the marker types.</p>
 */
@Configuration
@EnableConfigurationProperties(ContessaProperties.class)
public class ContessaAutoConfiguration implements Loggable {

    @Bean
    @ConditionalOnBean(Storage.class)
    @ConditionalOnMissingBean
    public ContentsService contessaContentsService(Storage storage) {

        return log_created(new ContentsServiceImpl(storage));
    }


    @Bean
    @ConditionalOnMissingBean
    public ValidationService validationService() {

        return log_created(new ValidationServiceImpl());
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.store", havingValue = "NONE")
    @ComponentScan(basePackageClasses = ContessaNone.class)
    public static class NoneStorageAutoConfiguration implements Loggable {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.store", havingValue = "LOCAL")
    @ComponentScan(basePackageClasses = ContessaLocal.class)
    public static class LocalStorageAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.store", havingValue = "NOSQL")
    @ComponentScan(basePackageClasses = ContessaNoSql.class)
    public static class NoSqlStorageAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.store", havingValue = "SQL")
    @ComponentScan(basePackageClasses = ContessaSql.class)
    public static class SqlStorageAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.ui.rest.enabled", havingValue = "true")
    @ComponentScan(basePackageClasses = ContessaRest.class)
    public static class RestUiAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.ui.web.enabled", havingValue = "true")
    @ComponentScan(basePackageClasses = ContessaWeb.class)
    public static class WebUiAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.ui.amqp.enabled", havingValue = "true")
    @ComponentScan(basePackageClasses = ContessaAmqp.class)
    public static class AmqpUiAutoConfiguration implements Loggable {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.ui.file.enabled", havingValue = "true")
    public static class FileUiAutoConfiguration implements Loggable {

        // TODO
    }
}
