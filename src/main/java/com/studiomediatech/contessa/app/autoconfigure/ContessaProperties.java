package com.studiomediatech.contessa.app.autoconfigure;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;


@Validated
@ConfigurationProperties(prefix = "contessa")
public class ContessaProperties {

    public static final String SQL = "SQL";
    public static final String FILE = "FILE";
    public static final String MONGO = "MONGO";
    public static final String RAM = "RAM";
    public static final String MINIO = "MINIO";
    public static final String CASSANDRA = "CASSANDRA";
    public static final String REDIS = "REDIS";
    public static final String NONE = "NONE";

    /**
     * The required base directory for Contessa file system use, such as file storage or drop-boxes.
     */
    @NotEmpty
    private String baseDir;

    /**
     * The storage type to use, may not be empty.
     */
    @NotEmpty
    private String store;

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

    /**
     * Optional IP address whitelist, for content upload to the REST and WEB user interfaces.
     */
    private List<String> allowedAddresses = new ArrayList<>();

    public String getBaseDir() {

        return baseDir;
    }


    public void setBaseDir(String baseDir) {

        this.baseDir = baseDir;
    }


    public String getStore() {

        return store;
    }


    public void setStore(String store) {

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


    public List<String> getAllowedAddresses() {

        return allowedAddresses;
    }


    public void setAllowedAddresses(List<String> allowedAddresses) {

        this.allowedAddresses = allowedAddresses;
    }
}
