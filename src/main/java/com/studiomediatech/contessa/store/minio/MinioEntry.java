package com.studiomediatech.contessa.store.minio;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.studiomediatech.contessa.domain.Entry;


class MinioEntry {

    public final String identifier;
    public final String suffix;
    public final String mimeType;
    public final byte[] data;

    @JsonCreator
    private MinioEntry( // NOSONAR
        @JsonProperty("identifier") String identifier, // NOSONAR
        @JsonProperty("suffix") String suffix, // NOSONAR
        @JsonProperty("mimeType") String mimeType, // NOSONAR
        @JsonProperty("data") byte[] data) {

        this.identifier = identifier;
        this.suffix = suffix;
        this.mimeType = mimeType;
        this.data = data;
    }

    public static MinioEntry valueOf(Entry entry) {

        return new MinioEntry(entry.getId(), entry.getSuffix(), entry.getType(), entry.getData());
    }


    public Entry asEntry() {

        Entry target = new Entry();

        target.setId(identifier);
        target.setSuffix(suffix);
        target.setType(mimeType);
        target.setData(data);

        return target;
    }
}
