package com.studiomediatech.contessa.storage.none;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.storage.Storage;


/**
 * A blank, null or demo-storage that actually does not store any data persistently at all.
 */
public class NoneStorageImpl implements Storage, Loggable {

    @Override
    public void store(Entry entry) {

        logger().warn("Not storing entry {}", entry);
    }
}
