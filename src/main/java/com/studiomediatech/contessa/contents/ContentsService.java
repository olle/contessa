package com.studiomediatech.contessa.contents;

/**
 * Defines the internal contents service API and the capabilities to handle and manage contents.
 */
public interface ContentsService {

    /**
     * Add the given data as a new media contents entry.
     *
     * @param  name  of the contents file to add
     * @param  payload  byte array to add
     */
    void addMediaContent(String name, byte[] payload);
}
