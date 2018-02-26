package com.studiomediatech.contessa.store.mongo;

import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackageClasses = ContessaMongo.class)
@Import(
    {
        MongoAutoConfiguration.class, // NOSONAR
        MongoDataAutoConfiguration.class // NOSONAR
    }
)
public class MongoDbConfig {

    // OK
}
