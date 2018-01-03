package com.studiomediatech.contessa.storage.sql;

import com.studiomediatech.contessa.domain.Entry;

import org.springframework.core.convert.converter.Converter;

import org.springframework.stereotype.Component;


@Component
public class DbEntryToEntryConverter implements Converter<SqlEntry, Entry> {

    @Override
    public Entry convert(SqlEntry source) {

        return null;
    }
}
