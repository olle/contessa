package com.studiomediatech.contessa.storage.db;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;


@Entity
public class DbEntry extends AbstractPersistable<Long> {

    private String identifier;
    private String suffix;
}
