package com.studiomediatech.contessa.storage.none;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.storage.Storage;

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
    public Optional<Entry> retrieve(String id, String suffix) {

        logger().warn("Will never retrieve any entries!");

        return Optional.empty();
    }
}
