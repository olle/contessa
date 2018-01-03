package com.studiomediatech.contessa.storage.sql;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration("SQL")
@EnableJpaRepositories(basePackageClasses = ContessaSql.class)
@EntityScan(basePackageClasses = ContessaSql.class)
@Import(
    {
        DataSourceAutoConfiguration.class, // NOSONAR
        DataSourceTransactionManagerAutoConfiguration.class, // NOSONAR
        HibernateJpaAutoConfiguration.class // NOSONAR
    }
)
public class Config {

    // OK
}
