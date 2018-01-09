package com.studiomediatech.contessa.store.local;

import com.studiomediatech.contessa.domain.Entry;


class LocalEntry {

    public final String identifier;
    public final String suffix;
    public final String mimeType;
    public final byte[] data;

    private LocalEntry(String identifier, String suffix, String mimeType, byte[] data) {

        this.identifier = identifier;
        this.suffix = suffix;
        this.mimeType = mimeType;
        this.data = data;
    }

    public static LocalEntry valueOf(Entry entry) {

        return new LocalEntry(entry.getId(), entry.getSuffix(), entry.getType(), entry.getData());
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
