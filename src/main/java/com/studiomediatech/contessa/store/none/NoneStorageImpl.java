package com.studiomediatech.contessa.store.none;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.store.Storage;

import java.util.Optional;


/**
 * A blank, null or demo-storage that actually does not store any data persistently at all.
 */
public class NoneStorageImpl implements Storage, Loggable {

    @Override
    public void store(Entry entry) {

        logger().warn("Not storing entry {}", entry);
    }


    @Override
    public Optional<Entry> retrieve(String identifier) {

        logger().warn("Not retrieving anything!");

        return Optional.empty();
    }
}
