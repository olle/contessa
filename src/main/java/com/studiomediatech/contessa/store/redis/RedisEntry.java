package com.studiomediatech.contessa.store.redis;

import com.studiomediatech.contessa.domain.Entry;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


@RedisHash("contents")
public class RedisEntry implements Serializable {

    @Id
    private String identifier;

    private String suffix;
    private String type;
    private byte[] data;

    public String getIdentifier() {

        return identifier;
    }


    public void setIdentifier(String identifier) {

        this.identifier = identifier;
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


    public static RedisEntry valueOf(Entry entry) {

        RedisEntry target = new RedisEntry();

        target.setIdentifier(entry.getId());
        target.setSuffix(entry.getSuffix());
        target.setType(entry.getType());
        target.setData(entry.getData());

        return target;
    }


    public Entry asEntry() {

        Entry target = new Entry();

        target.setId(identifier);
        target.setSuffix(suffix);
        target.setType(type);
        target.setData(data);

        return target;
    }
}
