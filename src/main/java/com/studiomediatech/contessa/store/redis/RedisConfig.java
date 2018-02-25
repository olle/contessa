package com.studiomediatech.contessa.store.redis;

import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@Configuration
@EnableRedisRepositories(basePackageClasses = ContessaRedis.class)
@Import(
    {
        RedisAutoConfiguration.class, // NOSONAR
        RedisRepositoriesAutoConfiguration.class // NOSONAR
    }
)
public class RedisConfig {

    // OK
}
