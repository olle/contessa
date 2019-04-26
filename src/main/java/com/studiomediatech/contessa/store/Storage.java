package com.studiomediatech.contessa.store;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.store.minio.ContessaException;

import java.util.Optional;


/**
 * Declares the API for storing and retrieving content data assets.
 */
public interface Storage extends Loggable {

    /**
     * Stores an entry under a default path.
     * 
     * @param entry the entry to store under default path. 
     */
    void store(Entry entry);
    
    /**
     *  Check if an entry already exists under gioven path.
     * 
     * @param entry the entry to check
     * @param path the pasth to the entry, possibly a bucket, possibly a url/uri, can be null
     * @return true if this entry is already saved under this path
     */
    boolean exists(Entry entry, String path) throws ContessaException;


    /**
     * Gets the enty identified by identifier under default path.
     * 
     * @param identifier the identifier of the entry 
     * @return the entry is exists 
     */
    Optional<Entry> retrieve(String identifier);
    
    /**
     * Remove an object from a given bucket if exists.
     * 
     * @param entry to remove
     * @param path a bucket if not default , an url/uri , can be null
     * @throws ContessaException 
     */
    void remove(Entry entry, String path) throws ContessaException;


    /**
     * Provides count of objects in all buckets.
     * 
     * @return entry count 
     */
    default long count() {

        return 0L;
    }
}
