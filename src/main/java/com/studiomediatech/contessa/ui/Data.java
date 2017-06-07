package com.studiomediatech.contessa.ui;

/**
 * Data struct type, representing user data being uploaded.
 */
public final class Data {

    private String filename;
    private byte[] payload;
    private String correlation;

    public String getFilename() {

        return filename;
    }


    public void setFilename(String filename) {

        this.filename = filename;
    }


    public byte[] getPayload() {

        return payload;
    }


    public void setPayload(byte[] payload) {

        this.payload = payload;
    }


    public String getCorrelation() {

        return correlation;
    }


    public void setCorrelation(String correlation) {

        this.correlation = correlation;
    }
}
