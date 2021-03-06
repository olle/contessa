package com.studiomediatech.contessa.store.sql;

import com.studiomediatech.contessa.domain.Entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity
@Table(name = "contents")
public class SqlEntry {

    @Id
    private String identifier;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "mime_type")
    private String type;

    @Lob
    @Column(name = "data")
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


    public static SqlEntry valueOf(Entry source) {

        SqlEntry target = new SqlEntry();

        target.setIdentifier(source.getId());
        target.setSuffix(source.getSuffix());
        target.setType(source.getType());
        target.setData(source.getData());

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
