package com.studiomediatech.contessa.storage.sql;

import com.studiomediatech.contessa.domain.Entry;

import org.springframework.core.convert.converter.Converter;

import org.springframework.stereotype.Component;


@Component
public class EntryToDbEntryConverter implements Converter<Entry, SqlEntry> {

    @Override
    public SqlEntry convert(Entry source) {

        SqlEntry target = new SqlEntry();

        target.setIdentifier(source.getId());
        target.setSuffix(source.getSuffix());
        target.setType(source.getType());
        target.setData(source.getData());

        return target;
    }
}
