package com.studiomediatech.contessa.storage.db;

import com.studiomediatech.contessa.domain.Entry;

import org.springframework.core.convert.converter.Converter;


public class EntryToDbEntryConverter implements Converter<Entry, DbEntry> {

    @Override
    public DbEntry convert(Entry source) {

        return new DbEntry();
    }
}
