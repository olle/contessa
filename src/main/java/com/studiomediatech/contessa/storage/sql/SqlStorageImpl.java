package com.studiomediatech.contessa.storage.sql;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.storage.Storage;

import org.springframework.core.convert.converter.Converter;

import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class SqlStorageImpl implements Storage, Loggable {

    private final Converter<Entry, SqlEntry> encoder;
    private final Converter<SqlEntry, Entry> decoder;
    private final SqlEntryRepository repo;

    public SqlStorageImpl(Converter<SqlEntry, Entry> decoder, Converter<Entry, SqlEntry> encoder,
        SqlEntryRepository repo) {

        this.decoder = decoder;
        this.encoder = encoder;
        this.repo = repo;
    }

    @Override
    public void store(Entry entry) {

        repo.save(encoder.convert(entry));
    }


    @Override
    public Optional<Entry> retrieve(String id, String suffix) {

        return repo.findOneByIdentifier(id).map(decoder::convert);
    }
}
