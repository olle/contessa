package com.studiomediatech.contessa.ui;

/**
 * A data structure type, encapsulating an uploaded content request.
 */
public final class UploadRequest {

    public final String filename;
    public final byte[] payload;

    private UploadRequest(String filename, byte[] payload) {

        this.filename = filename;
        this.payload = payload;
    }

    public static UploadRequest valueOf(String filename, byte[] payload) {

        return new UploadRequest(filename, payload);
    }
}
