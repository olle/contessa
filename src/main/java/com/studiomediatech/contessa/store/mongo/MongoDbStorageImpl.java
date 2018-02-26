package com.studiomediatech.contessa.store.mongo;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.store.Storage;

import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class MongoDbStorageImpl implements Storage, Loggable {

    private final MongoDbEntryRepository repo;

    public MongoDbStorageImpl(MongoDbEntryRepository repo) {

        this.repo = repo;
    }

    @Override
    public void store(Entry entry) {

        MongoDbEntry stored = repo.save(MongoDbEntry.valueOf(entry));
        logger().debug("Saved {}", stored);
    }


    @Override
    public Optional<Entry> retrieve(String identifier) {

        Optional<MongoDbEntry> entry = repo.findOneByIdentifier(identifier);
        logger().debug("Retrieved {} for identifier: {}", entry, identifier);

        return entry.map(MongoDbEntry::asEntry);
    }


    @Override
    public long count() {

        return repo.count();
    }
}
