package com.studiomediatech.contessa.contents;

import com.studiomediatech.contessa.domain.Entry;

import java.util.Optional;


/**
 * Defines the internal contents service API and the capabilities to handle and manage contents.
 */
public interface ContentsService {

    /**
     * Add the given data as a new media contents entry.
     *
     * @param  name  of the contents file to add
     * @param  payload  byte array to add
     *
     * @return  the added media content entry
     */
    Entry addMediaContent(String name, byte[] payload);


    /**
     * Fetch the media content data for the given identifier.
     *
     * @param  identifier  predicate, used to resolve the media content to fetch
     *
     * @return  the media entry object found, may be empty but never {@code null}
     */
    Optional<Entry> getMediaContentForIdentifier(String identifier);


    /**
     * Fetch the media content data for the given name - an identifier and suffix.
     *
     * @param  name  predicate, used to resolve the media content entry by identifier and suffix
     *
     * @return  the media entry object found, may be empty but never {@code null}
     */
    Optional<Entry> getMediaContentForName(String name);


    long getMediaContentCount();
}
