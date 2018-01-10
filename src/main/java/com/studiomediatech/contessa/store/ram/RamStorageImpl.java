package com.studiomediatech.contessa.store.ram;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.store.Storage;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Component
public class RamStorageImpl implements Storage, Loggable {

    // Entries are read-only, so thread safety is less important here.
    private static final Map<String, Entry> storage = new HashMap<>();

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
