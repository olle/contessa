package com.studiomediatech.contessa.store.cassandra;

import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableCassandraRepositories(basePackageClasses = ContessaCassandra.class)
@Import(CassandraDataAutoConfiguration.class)
public class CassandraConfig extends AbstractCassandraConfiguration {

    private CassandraProperties props;

    public CassandraConfig(CassandraProperties props) {

        this.props = props;
    }

    @Override
    public SchemaAction getSchemaAction() {

        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }


    @Override
    protected List<String> getStartupScripts() {

        return Arrays.asList(String.format(
                    "CREATE KEYSPACE IF NOT EXISTS %s "
                    + "WITH REPLICATION = { 'class':'SimpleStrategy', 'replication_factor': 1};",
                    props.getKeyspaceName()));
    }


    @Override
    protected String getKeyspaceName() {

        return props.getKeyspaceName();
    }
}
