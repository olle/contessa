package com.studiomediatech.contessa.store.redis;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@Configuration
@EnableRedisRepositories(basePackageClasses = ContessaRedis.class)
@EntityScan(basePackageClasses = ContessaRedis.class)
public class RedisConfig {

    // OK
}
