package com.studiomediatech.contessa.store.ram;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.store.Storage;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class RamStorageImpl implements Storage, Loggable {

    // Initial high load-factory to avoid early resize-costs.
    private static final Map<String, Entry> storage = new ConcurrentHashMap<>(65536);

    @Override
    public void store(Entry entry) {

        storage.put(entry.getId(), entry);
    }


    @Override
    public Optional<Entry> retrieve(String identifier) {

        return Optional.ofNullable(storage.get(identifier));
    }


    @Override
    public long count() {

        return storage.size();
    }
}
