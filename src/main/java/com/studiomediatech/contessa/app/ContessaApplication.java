package com.studiomediatech.contessa.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpEncodingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;

import org.springframework.context.annotation.AdviceMode;

import org.springframework.scheduling.annotation.EnableAsync;


/**
 * Application starter.
 *
 * <p>The big exclude-list is due to some extended custom configurations that we do, in order to enable modules
 * selectively by configuration properties.</p>
 */
@SpringBootApplication(
    exclude = {
        HttpEncodingAutoConfiguration.class, // NOSONAR
        MetricFilterAutoConfiguration.class, // NOSONAR
        ErrorMvcAutoConfiguration.class, // NOSONAR
        WebMvcAutoConfiguration.class, // NOSONAR
        RabbitAutoConfiguration.class, // NOSONAR
        MongoDataAutoConfiguration.class, // NOSONAR
        MongoAutoConfiguration.class, // NOSONAR
        DataSourceAutoConfiguration.class, // NOSONAR
        HibernateJpaAutoConfiguration.class, // NOSONAR
        CassandraDataAutoConfiguration.class, // NOSONAR
        RedisAutoConfiguration.class // NOSONAR
    }
)
@EnableAsync(mode = AdviceMode.PROXY, proxyTargetClass = true)
public class ContessaApplication {

    public static void main(String[] args) {

        SpringApplication.run(ContessaApplication.class, args);
    }
}
