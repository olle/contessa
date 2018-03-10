package com.studiomediatech.contessa.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

import org.springframework.context.annotation.AdviceMode;

import org.springframework.scheduling.annotation.EnableAsync;


/**
 * Application starter.
 *
 * <p>The big exclude-list is due to some extended custom configurations that we do, in order to enable modules
 * selectively by configuration properties, instead of only by existing as a dependency.</p>
 *
 * <p><em>NOTE: Most Spring Boot dependencies are (by design) activated as they are added to the project
 * POM-file.</em></p>
 */
@SpringBootApplication(
    //J-
    exclude = {
        HttpEncodingAutoConfiguration.class,
        ErrorMvcAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        RabbitAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        MongoAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        CassandraDataAutoConfiguration.class,
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class
    }
    //J+
)
@EnableAsync(mode = AdviceMode.PROXY, proxyTargetClass = true)
public class ContessaApplication {

    public static void main(String[] args) {

        SpringApplication.run(ContessaApplication.class, args);
    }
}
