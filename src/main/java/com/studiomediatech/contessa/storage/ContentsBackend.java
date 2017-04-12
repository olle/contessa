package com.studiomediatech.contessa.storage;

/**
 * Wrapper interface for Contessa pluggable backends.
 */
public interface ContentsBackend {

    void store(String id, String type, String name, byte[] data);


    byte[] load(String id);
}
