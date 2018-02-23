package com.studiomediatech.contessa.store.minio;

import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.validation.annotation.Validated;


@Validated
@ConfigurationProperties(prefix = "minio")
public class ContessaMinioProperties {

    @NotEmpty
    private String endpoint;
    @NotEmpty
    private String accessKey;
    @NotEmpty
    private String secretKey;

    public String getEndpoint() {

        return endpoint;
    }


    public void setEndpoint(String endpoint) {

        this.endpoint = endpoint;
    }


    public String getAccessKey() {

        return accessKey;
    }


    public void setAccessKey(String accessKey) {

        this.accessKey = accessKey;
    }


    public String getSecretKey() {

        return secretKey;
    }


    public void setSecretKey(String secretKey) {

        this.secretKey = secretKey;
    }
}
