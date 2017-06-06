package com.studiomediatech.contessa.storage.db;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.storage.Storage;

import org.springframework.core.convert.converter.Converter;

import java.util.Optional;


public class DbStorageImpl implements Storage, Loggable {

    private final Converter<Entry, DbEntry> encoder;
    private final Converter<DbEntry, Entry> decoder;
    private final DbEntryRepository repo;

    public DbStorageImpl(Converter<DbEntry, Entry> decoder, Converter<Entry, DbEntry> encoder,
        DbEntryRepository repo) {

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

        return repo.findOneByIdentifierAndSuffix(id, suffix).map(decoder::convert);
    }
}
