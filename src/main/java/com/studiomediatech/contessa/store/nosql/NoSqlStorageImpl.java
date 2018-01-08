package com.studiomediatech.contessa.store.nosql;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.store.Storage;

import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class NoSqlStorageImpl implements Storage, Loggable {

    private final NoSqlEntryRepository repo;

    public NoSqlStorageImpl(NoSqlEntryRepository repo) {

        this.repo = repo;
    }

    @Override
    public void store(Entry entry) {

        NoSqlEntry stored = repo.save(NoSqlEntry.valueOf(entry));
        logger().debug("Saved {}", stored);
    }


    @Override
    public Optional<Entry> retrieve(String identifier) {

        Optional<NoSqlEntry> entry = repo.findOneByIdentifier(identifier);
        logger().debug("Retrieved {} for identifier: {}", entry, identifier);

        return entry.map(NoSqlEntry::asEntry);
    }


    @Override
    public long count() {

        return repo.count();
    }
}
