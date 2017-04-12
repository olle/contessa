package com.studiomediatech.contessa.contents;

import com.studiomediatech.contessa.Loggable;
import com.studiomediatech.contessa.contents.media.HashCodePrefixGenerator;
import com.studiomediatech.contessa.contents.media.RegExSuffixParser;
import com.studiomediatech.contessa.storage.ContentsBackend;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;


/**
 * Simple MVP implementation of the contents service.
 */
public class ContentsServiceImpl implements ContentsService, Loggable {

    private final ContentsBackend contentsBackend;
    private final RegExSuffixParser suffixParser;
    private final HashCodePrefixGenerator prefixGenerator;

    @Autowired
    public ContentsServiceImpl(ContentsBackend contentsBackend) {

        this.contentsBackend = contentsBackend;
        this.prefixGenerator = new HashCodePrefixGenerator();
        this.suffixParser = new RegExSuffixParser();
    }

    @Override
    public void addMediaContent(String name, byte[] payload) {

        if (logger().isDebugEnabled()) {
            logPayloadExcerpt(payload);
        }

        String prefix = prefixGenerator.getPrefix(name, payload);
        String suffix = suffixParser.getSuffix(name);

        logger().info("Delegating to storage: {}, {}, {} and {} bytes of data", prefix, suffix, name, payload.length);

        contentsBackend.store(prefix, suffix, name, payload);
    }


    @Override
    public Map<String, Object> getMediaContent(String id) {

        String code = suffixParser.getPrefix(id);

        byte[] load = contentsBackend.load(code);

        Map<String, Object> map = new HashMap<>();
        map.put("image", load);

        return map;
    }


    private void logPayloadExcerpt(byte[] payload) {

        int len = Math.min(8, payload.length);
        byte[] excerpt = new byte[len];
        System.arraycopy(payload, 0, excerpt, 0, len);
        logger().debug("Data payload begins {}...", excerpt);
    }
}
