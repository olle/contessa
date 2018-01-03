package com.studiomediatech.contessa.storage.sql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "contents")
public class DbEntry {

    @Id
    private String identifier;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "data")
    private byte[] data;

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
