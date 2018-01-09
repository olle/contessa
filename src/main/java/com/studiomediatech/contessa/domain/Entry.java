package com.studiomediatech.contessa.domain;

public class Entry {

    private String id;
    private String suffix;
    private String type;
    private byte[] data;

    public String getId() {

        return id;
    }


    public void setId(String id) {

        this.id = id;
    }


    public String getSuffix() {

        return suffix;
    }


    public void setSuffix(String suffix) {

        this.suffix = suffix;
    }


    public String getType() {

        return type;
    }


    public void setType(String type) {

        this.type = type;
    }


    public byte[] getData() {

        return data;
    }


    public void setData(byte[] data) {

        this.data = data;
    }
}
