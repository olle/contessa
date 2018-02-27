package com.studiomediatech.contessa.store.cassandra;

import com.studiomediatech.contessa.domain.Entry;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;


@Table(value = "contents")
public class CassandraEntry {

    @PrimaryKey
    private String identifier;

    private String suffix;
    private String type;
    private ByteBuffer data;

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


    public ByteBuffer getData() {

        return data;
    }


    public void setData(ByteBuffer data) {

        this.data = data;
    }


    public static CassandraEntry valueOf(Entry entry) {

        CassandraEntry target = new CassandraEntry();

        target.setIdentifier(entry.getId());
        target.setSuffix(entry.getSuffix());
        target.setType(entry.getType());
        target.setData(ByteBuffer.wrap(entry.getData()));

        return target;
    }


    public Entry asEntry() {

        Entry target = new Entry();

        target.setId(identifier);
        target.setSuffix(suffix);
        target.setType(type);
        target.setData(data.array());

        return target;
    }
}
