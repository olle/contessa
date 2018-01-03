package com.studiomediatech.contessa.storage.sql;

import com.studiomediatech.contessa.domain.Entry;

import org.springframework.core.convert.converter.Converter;

import org.springframework.stereotype.Component;


@Component
public class EntryToDbEntryConverter implements Converter<Entry, SqlEntry> {

    @Override
    public SqlEntry convert(Entry source) {

        return new SqlEntry();
    }
}
