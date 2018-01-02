package com.studiomediatech.contessa.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


/**
 * Application starter.
 */
@SpringBootApplication(
    exclude = {
        RabbitAutoConfiguration.class, // NOSONAR
        MongoDataAutoConfiguration.class, // NOSONAR
        MongoAutoConfiguration.class, // NOSONAR
        DataSourceAutoConfiguration.class, // NOSONAR
        DataSourceTransactionManagerAutoConfiguration.class, // NOSONAR
        HibernateJpaAutoConfiguration.class // NOSONAR
    }
)
public class ContessaApplication {

    public static void main(String[] args) {

        SpringApplication.run(ContessaApplication.class, args);
    }
}
