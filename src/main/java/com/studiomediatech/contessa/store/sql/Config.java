package com.studiomediatech.contessa.store.sql;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration("SQL")
@EnableJpaRepositories(basePackageClasses = ContessaSql.class)
@EntityScan(basePackageClasses = ContessaSql.class)
@EnableTransactionManagement(proxyTargetClass = true)
@Import(
    {
        DataSourceAutoConfiguration.class, // NOSONAR
        HibernateJpaAutoConfiguration.class // NOSONAR
    }
)
public class Config {

    // OK
}
