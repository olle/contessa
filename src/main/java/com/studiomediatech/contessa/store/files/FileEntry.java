package com.studiomediatech.contessa.store.files;

import com.studiomediatech.contessa.domain.Entry;


class FileEntry {

    public final String identifier;
    public final String suffix;
    public final String mimeType;
    public final byte[] data;

    private FileEntry(String identifier, String suffix, String mimeType, byte[] data) {

        this.identifier = identifier;
        this.suffix = suffix;
        this.mimeType = mimeType;
        this.data = data;
    }

    public static FileEntry valueOf(Entry entry) {

        return new FileEntry(entry.getId(), entry.getSuffix(), entry.getType(), entry.getData());
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
