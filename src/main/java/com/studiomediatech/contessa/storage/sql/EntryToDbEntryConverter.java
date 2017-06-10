package com.studiomediatech.contessa.storage.sql;

import com.studiomediatech.contessa.domain.Entry;

import org.springframework.core.convert.converter.Converter;

import org.springframework.stereotype.Component;


@Component
public class EntryToDbEntryConverter implements Converter<Entry, DbEntry> {

    @Override
    public DbEntry convert(Entry source) {

        return new DbEntry();
    }
}
