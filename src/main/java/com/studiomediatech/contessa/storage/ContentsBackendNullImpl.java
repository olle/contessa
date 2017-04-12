package com.studiomediatech.contessa.storage;

import com.studiomediatech.contessa.Loggable;


/**
 * A contents storage backend, that does _not_ persist anything, doesn't return anything and only logs.
 */
public class ContentsBackendNullImpl implements ContentsBackend, Loggable {

    @Override
    public void store(String id, String type, String name, byte[] data) {

        logger().warn(
            "Not storing payload of {} bytes for identifier: '{}' type: '{}' and original name '{}' - ignored in this implementation",
            data.length, id, type, name);
    }


    @Override
    public byte[] load(String id) {

        logger().warn("Not loading anyting for '{}' from backend - this is an empty implementation, returning `null`.",
            id);

        return null;
    }
}
