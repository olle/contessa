package com.studiomediatech.contessa.contents.media;

/**
 * Thrown when handling of a content entry could not be completed. For example when receiving a content entry that
 * cannot be identified by it's name, or in case an unwanted content type is received.
 */
public class UnsupportedMediaTypeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnsupportedMediaTypeException(String message) {

        super(message);
    }
}
