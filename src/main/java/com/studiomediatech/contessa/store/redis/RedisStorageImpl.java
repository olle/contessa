package com.studiomediatech.contessa.store.redis;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.store.Storage;

import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class RedisStorageImpl implements Storage, Loggable {

    private final RedisEntryRepository repo;

    public RedisStorageImpl(RedisEntryRepository repo) {

        this.repo = repo;
    }

    @Override
    public void store(Entry entry) {

        RedisEntry stored = repo.save(RedisEntry.valueOf(entry));
        logger().debug("Saved {}", stored);
    }


    @Override
    public Optional<Entry> retrieve(String identifier) {

        Optional<RedisEntry> entry = Optional.ofNullable(repo.findOne(identifier));
        logger().debug("Retrieved {} for identifier: {}", entry, identifier);

        return entry.map(RedisEntry::asEntry);
    }


    @Override
    public long count() {

        return repo.count();
    }
}
