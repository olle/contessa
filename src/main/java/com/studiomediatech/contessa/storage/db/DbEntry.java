package com.studiomediatech.contessa.storage.db;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;


@Entity
public class DbEntry extends AbstractPersistable<Long> {

    private static final long serialVersionUID = 1L;

    private String identifier;
    private String suffix;

    public String getIdentifier() {

        return identifier;
    }


    public void setIdentifier(String identifier) {

        this.identifier = identifier;
    }


    public String getSuffix() {

        return suffix;
    }


    public void setSuffix(String suffix) {

        this.suffix = suffix;
    }
}
