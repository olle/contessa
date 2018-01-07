package com.studiomediatech.contessa.storage.sql;

import com.studiomediatech.contessa.domain.Entry;

import org.springframework.core.convert.converter.Converter;

import org.springframework.stereotype.Component;


@Component
public class DbEntryToEntryConverter implements Converter<SqlEntry, Entry> {

    @Override
    public Entry convert(SqlEntry source) {

        Entry target = new Entry();

        target.setId(source.getIdentifier());
        target.setData(source.getData());
        target.setSuffix(source.getSuffix());
        target.setType(source.getType());

        return target;
    }
}
