package com.studiomediatech.contessa.storage.db;

import com.studiomediatech.contessa.domain.Entry;

import org.springframework.core.convert.converter.Converter;

import org.springframework.stereotype.Component;


@Component
public class DbEntryToEntryConverter implements Converter<DbEntry, Entry> {

    @Override
    public Entry convert(DbEntry source) {

        return null;
    }
}
