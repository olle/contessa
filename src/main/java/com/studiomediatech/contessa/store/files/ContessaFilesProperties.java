package com.studiomediatech.contessa.store.files;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;


@ConfigurationProperties(prefix = "contessa.files")
public class ContessaFilesProperties {

    /**
     * The base directory for Contessa files storage.
     */
    @NotNull
    private String baseDir = "";

    public String getBaseDir() {

        return baseDir;
    }


    public void setBaseDir(String baseDir) {

        this.baseDir = baseDir;
    }
}
