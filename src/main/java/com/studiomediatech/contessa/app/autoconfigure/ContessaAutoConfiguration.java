package com.studiomediatech.contessa.app.autoconfigure;

import com.studiomediatech.contessa.contents.ContentsService;
import com.studiomediatech.contessa.contents.ContentsServiceImpl;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.store.Storage;
import com.studiomediatech.contessa.store.cassandra.ContessaCassandra;
import com.studiomediatech.contessa.store.file.ContessaFile;
import com.studiomediatech.contessa.store.minio.ContessaMinio;
import com.studiomediatech.contessa.store.mongo.ContessaMongo;
import com.studiomediatech.contessa.store.none.ContessaNone;
import com.studiomediatech.contessa.store.ram.ContessaRam;
import com.studiomediatech.contessa.store.sql.ContessaSql;
import com.studiomediatech.contessa.ui.amqp.ContessaAmqp;
import com.studiomediatech.contessa.ui.dropbox.ContessaDir;
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
    @ConditionalOnProperty(name = "contessa.store", havingValue = ContessaProperties.NONE)
    @ComponentScan(basePackageClasses = ContessaNone.class)
    public static class NoneStorageAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.store", havingValue = ContessaProperties.FILE)
    @ComponentScan(basePackageClasses = ContessaFile.class)
    public static class FileStorageAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.store", havingValue = ContessaProperties.MONGO)
    @ComponentScan(basePackageClasses = ContessaMongo.class)
    public static class MongoDbStorageAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.store", havingValue = ContessaProperties.SQL)
    @ComponentScan(basePackageClasses = ContessaSql.class)
    public static class SqlStorageAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.store", havingValue = ContessaProperties.RAM)
    @ComponentScan(basePackageClasses = ContessaRam.class)
    public static class RamStorageAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.store", havingValue = ContessaProperties.MINIO)
    @ComponentScan(basePackageClasses = ContessaMinio.class)
    public static class MinioStorageAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.store", havingValue = ContessaProperties.CASSANDRA)
    @ComponentScan(basePackageClasses = ContessaCassandra.class)
    public static class CassandraStorageAutoConfiguration {

        // OK
    }

//    @ConditionalOnProperty(name = "contessa.store", havingValue = ContessaProperties.REDIS)
//    @Configuration
//    @Import(
//        {
//            RedisAutoConfiguration.class, // NOSONAR
//            RedisRepositoriesAutoConfiguration.class // NOSONAR
//        }
//    )
//    public static class RedisStorageAutoConfigurationWrapper {
//
//        @Configuration
//        @ConditionalOnProperty(name = "contessa.store", havingValue = ContessaProperties.REDIS)
//        @EnableRedisRepositories(basePackageClasses = ContessaRedis.class)
//        @EntityScan(basePackageClasses = ContessaRedis.class)
//        @ComponentScan(basePackageClasses = ContessaRedis.class)
//        public static class RedisStorageAutoConfiguration {
//
//            // OK
//        }
//    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.rest.enabled", havingValue = "true")
    @ComponentScan(basePackageClasses = ContessaRest.class)
    public static class RestUiAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.web.enabled", havingValue = "true")
    @ComponentScan(basePackageClasses = ContessaWeb.class)
    public static class WebUiAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.amqp.enabled", havingValue = "true")
    @ComponentScan(basePackageClasses = ContessaAmqp.class)
    public static class AmqpUiAutoConfiguration {

        // OK
    }

    @Configuration
    @ConditionalOnProperty(name = "contessa.dir.enabled", havingValue = "true")
    @ComponentScan(basePackageClasses = ContessaDir.class)
    public static class DirUiAutoConfiguration {

        // OK
    }
}
