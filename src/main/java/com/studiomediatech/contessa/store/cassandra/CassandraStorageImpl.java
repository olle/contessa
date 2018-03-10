package com.studiomediatech.contessa.store.cassandra;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.store.Storage;

import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class CassandraStorageImpl implements Storage, Loggable {

    private final CassandraEntryRepository repo;

    public CassandraStorageImpl(CassandraEntryRepository repo) {

        this.repo = repo;
    }

    @Override
    public void store(Entry entry) {

        CassandraEntry stored = repo.save(CassandraEntry.valueOf(entry));
        logger().debug("Saved {}", stored);
    }


    @Override
    public Optional<Entry> retrieve(String identifier) {

        Optional<CassandraEntry> entry = repo.findById(identifier);
        logger().debug("Retrieved {} for identifier: {}", entry, identifier);

        return entry.map(CassandraEntry::asEntry);
    }


    @Override
    public long count() {

        return repo.count();
    }
}
