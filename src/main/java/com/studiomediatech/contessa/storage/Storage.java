package com.studiomediatech.contessa.storage;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;

import java.util.Optional;


/**
 * Declares the API for storing and retrieving content data assets.
 */
public interface Storage extends Loggable {

    /**
     * Store the given entry, persisting it in a way that ensures that it can later be retrieved.
     *
     * @param  entry  to persist
     */
    void store(Entry entry);


    /**
     * Retrieves the entry for the given identifier.
     *
     * @param  identifier  of the entry to retrieve
     *
     * @return  the entry, may be empty but never {@code null}
     */
    default Optional<Entry> retrieve(String identifier) {

        logger().warn("Not retrieving anything for {}", identifier);

        return Optional.empty();
    }
}
