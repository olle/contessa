package com.studiomediatech.contessa.store.sql;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.store.Storage;

import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class SqlStorageImpl implements Storage, Loggable {

    private final SqlEntryRepository repo;

    public SqlStorageImpl(SqlEntryRepository repo) {

        this.repo = repo;
    }

    @Override
    public void store(Entry entry) {

        SqlEntry e = SqlEntry.valueOf(entry);

        SqlEntry stored = repo.save(e);
        logger().debug("Saved {}", stored);
    }


    @Override
    public Optional<Entry> retrieve(String identifier) {

        Optional<SqlEntry> entry = repo.findOneByIdentifier(identifier);
        logger().debug("Retrieved {} for identifier: {}", entry, identifier);

        return entry.map(SqlEntry::asEntry);
    }
}
