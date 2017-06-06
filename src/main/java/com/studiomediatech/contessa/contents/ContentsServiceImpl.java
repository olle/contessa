package com.studiomediatech.contessa.contents;

import com.studiomediatech.contessa.contents.media.HashCodePrefixGenerator;
import com.studiomediatech.contessa.contents.media.RegExSuffixParser;
import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.storage.Storage;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;


/**
 * Simple MVP implementation of the contents service.
 */
public class ContentsServiceImpl implements ContentsService, Loggable {

    private final RegExSuffixParser parser;
    private final HashCodePrefixGenerator prefixGenerator;
    private final Storage storage;

    @Autowired
    public ContentsServiceImpl(Storage storage) {

        this.storage = storage;
        this.prefixGenerator = new HashCodePrefixGenerator();
        this.parser = new RegExSuffixParser();
    }

    @Override
    public void addMediaContent(String name, byte[] payload) {

        if (logger().isDebugEnabled()) {
            logPayloadExcerpt(payload);
        }

        String prefix = prefixGenerator.getPrefix(name, payload);
        String suffix = parser.parseSuffix(name);

        logger().info("Delegating to storage: {}, {}, {} and {} bytes of data", prefix, suffix, name, payload.length);

        Entry e = new Entry();

        e.setId(prefix);
        e.setSuffix(suffix);
        e.setData(payload);

        storage.store(e);
    }


    @Override
    public Map<String, Object> getMediaContent(String id) {

        String prefix = parser.parsePrefix(id);
        String suffix = parser.parseSuffix(id);

        Map<String, Object> map = new HashMap<>();

        storage.retrieve(prefix, suffix).ifPresent(e -> map.put("image", e.getData()));

        return map;
    }


    private void logPayloadExcerpt(byte[] payload) {

        int len = Math.min(8, payload.length);
        byte[] excerpt = new byte[len];
        System.arraycopy(payload, 0, excerpt, 0, len);
        logger().debug("Data payload begins {}...", excerpt);
    }
}
