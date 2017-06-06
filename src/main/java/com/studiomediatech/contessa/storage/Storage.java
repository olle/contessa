package com.studiomediatech.contessa.storage;

import com.studiomediatech.contessa.domain.Entry;

import java.util.Optional;


/**
 * Declares the API for storing and retrieving content data assets.
 */
public interface Storage {

    /**
     * Store the given entry, persisting it in a way that ensures that it can later be retrieved.
     *
     * @param  entry  to persist
     */
    void store(Entry entry);


    /**
     * Retrieves the entry, identified by the given string identifier.
     *
     * @param  id  that identifies the entry to retrieve
     * @param  suffix  of the entry to find
     *
     * @return  the entry that was found, may be empty but never {@code null}
     */
    Optional<Entry> retrieve(String id, String suffix);
}
