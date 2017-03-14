package com.studiomediatech.contessa.contents;

import com.studiomediatech.contessa.Loggable;
import com.studiomediatech.contessa.storage.ContentsBackend;

import org.apache.tomcat.util.codec.binary.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Simple MVP implementation of the contents service.
 */
public class ContentsServiceImpl implements ContentsService, Loggable {

    private static final Pattern SUFFIXES = Pattern.compile("^[^.]+\\.(png|gif|jpg|svg|ico|js|css|woff|txt|html|pdf)$",
            Pattern.CASE_INSENSITIVE);

    private final ContentsBackend contentsBackend;

    @Autowired
    public ContentsServiceImpl(ContentsBackend contentsBackend) {

        this.contentsBackend = contentsBackend;
    }

    @Override
    public void addMediaContent(String name, byte[] payload) {

        if (logger().isDebugEnabled()) {
            logPayloadExcerpt(payload);
        }

        String prefix = getPrefix(name, payload);
        String suffix = getSuffix(name);

        logger().info("Delegating to storage: {}/{}/{} and {} bytes of data", prefix, suffix, name, payload.length);

        contentsBackend.store(prefix, suffix, name, payload);
    }


    @Override
    public Map<String, Object> getMediaContent(java.lang.String id) {

        return Collections.emptyMap();
    }


    String getPrefix(String name, byte[] payload) {

        int a = Arrays.hashCode(StringUtils.getBytesUtf8(name));
        int b = Arrays.hashCode(payload);

        String prefix = String.format("%x-%x", a, b);

        logger().debug("Generated {} for '{}' and {} bytes of data", prefix, name, payload.length);

        return prefix;
    }


    String getSuffix(String name) {

        Matcher matcher = SUFFIXES.matcher(name);

        if (matcher.matches()) {
            return matcher.group(1);
        }

        throw new UnsupportedMediaTypeException(String.format("Could not parse suffix for '%s'", name));
    }


    private void logPayloadExcerpt(byte[] payload) {

        int len = Math.min(8, payload.length);
        byte[] excerpt = new byte[len];
        System.arraycopy(payload, 0, excerpt, 0, len);
        logger().debug("Data payload begins {}...", excerpt);
    }
}
