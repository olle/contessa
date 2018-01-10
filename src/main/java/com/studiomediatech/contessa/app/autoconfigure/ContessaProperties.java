package com.studiomediatech.contessa.app.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;


@Validated
@ConfigurationProperties(prefix = "contessa")
public class ContessaProperties {

    public enum StorageType {

        NONE,
        FILES,
        SQL,
        NOSQL;
    }

    /**
     * The storage type to use, may not be empty.
     */
    @NotNull
    private StorageType store;

    /**
     * Optional list of cookies (names) that are required in any request.
     */
    private List<String> requiredCookies = new ArrayList<>();

    public StorageType getStore() {

        return store;
    }


    public void setStore(StorageType store) {

        this.store = store;
    }


    public List<String> getRequiredCookies() {

        return requiredCookies;
    }


    public void setRequiredCookies(List<String> requiredCookies) {

        this.requiredCookies = requiredCookies;
    }
}
