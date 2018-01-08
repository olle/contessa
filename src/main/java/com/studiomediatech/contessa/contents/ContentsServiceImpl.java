package com.studiomediatech.contessa.contents;

import com.studiomediatech.contessa.contents.media.ByteArrayMimeTypeParser;
import com.studiomediatech.contessa.contents.media.HashCodePrefixGenerator;
import com.studiomediatech.contessa.contents.media.RegExSuffixParser;
import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.store.Storage;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


/**
 * Simple MVP implementation of the contents service.
 */
public class ContentsServiceImpl implements ContentsService, Loggable {

    private final RegExSuffixParser parser;
    private final HashCodePrefixGenerator prefixGenerator;
    private final Storage storage;
    private final ByteArrayMimeTypeParser mimeTypeParser;

    @Autowired
    public ContentsServiceImpl(Storage storage) {

        this.storage = storage;
        this.prefixGenerator = new HashCodePrefixGenerator();
        this.parser = new RegExSuffixParser();
        this.mimeTypeParser = new ByteArrayMimeTypeParser();
    }

    @Override
    public Entry addMediaContent(String name, byte[] payload) {

        if (logger().isDebugEnabled()) {
            logPayloadExcerpt(payload);
        }

        String prefix = prefixGenerator.getPrefix(name, payload);
        String suffix = parser.parseSuffix(name);
        String mimeType = mimeTypeParser.parseMimeType(payload);

        logger().info("Delegating to storage: {}, {}, {}, {} and {} bytes of data", prefix, suffix, mimeType, name,
            payload.length);

        Entry entry = new Entry();

        entry.setId(prefix);
        entry.setSuffix(suffix);
        entry.setType(mimeType);
        entry.setData(payload);

        storage.store(entry);

        return entry;
    }


    @Override
    public Optional<Entry> getMediaContentForIdentifier(String identifier) {

        return storage.retrieve(identifier);
    }


    @Override
    public Optional<Entry> getMediaContentForName(String name) {

        return storage.retrieve(parser.parsePrefix(name));
    }


    private void logPayloadExcerpt(byte[] payload) {

        int len = Math.min(8, payload.length);
        byte[] excerpt = new byte[len];
        System.arraycopy(payload, 0, excerpt, 0, len);
        logger().debug("Data payload begins {}...", excerpt);
    }
}
