package com.studiomediatech.contessa.app.autoconfigure;

import org.hibernate.validator.constraints.Range;

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
        NOSQL,
        RAM;
    }

    /**
     * The required base directory for Contessa file system use, such as file storage or drop-boxes.
     */
    @NotNull
    private String baseDir;

    /**
     * The storage type to use, may not be empty.
     */
    @NotNull
    private StorageType store;

    /**
     * Optional list of cookies (names) that are required in any request.
     */
    private List<String> requiredCookies = new ArrayList<>();

    /**
     * The content max-age cache control directive, in seconds. Default is 100 days.
     *
     * <p>Currently the valid range is 0 to 365 days.</p>
     */
    @Range(min = 0L, max = 31536000L)
    private long maxAge = 8640000;

    public String getBaseDir() {

        return baseDir;
    }


    public void setBaseDir(String baseDir) {

        this.baseDir = baseDir;
    }


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


    public long getMaxAge() {

        return maxAge;
    }


    public void setMaxAge(long maxAge) {

        this.maxAge = maxAge;
    }
}
