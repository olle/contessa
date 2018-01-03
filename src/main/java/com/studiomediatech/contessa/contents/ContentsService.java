package com.studiomediatech.contessa.contents;

import java.util.Map;


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
     * @return  the unique content identifier
     */
    String addMediaContent(String name, byte[] payload);


    /**
     * Fetch the media content data for the given identifier.
     *
     * @param  id  predicate, used to resolve the media content to fetch
     *
     * @return  the media object found, never {@code null}
     */
    Map<String, Object> getMediaContent(String id);
}
