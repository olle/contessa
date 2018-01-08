package com.studiomediatech.contessa.app.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Validated
@ConfigurationProperties(prefix = "contessa")
public class ContessaProperties {

    public enum StorageType {

        NONE,
        LOCAL,
        SQL,
        NOSQL;
    }

    /**
     * The storage type to use, may not be empty.
     */
    @NotNull
    private StorageType store;

    /**
     * The base directory used by the {@link StorageType#LOCAL local} storage type.
     */
    private String baseDir;

    public StorageType getStore() {

        return store;
    }


    public void setStore(StorageType store) {

        this.store = store;
    }


    public String getBaseDir() {

        return baseDir;
    }


    public void setBaseDir(String baseDir) {

        this.baseDir = baseDir;
    }
}
