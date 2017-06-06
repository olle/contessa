package com.studiomediatech.contessa.contents.media;

import org.apache.tomcat.util.codec.binary.StringUtils;

import com.studiomediatech.contessa.logging.Loggable;

import java.util.Arrays;


public final class HashCodePrefixGenerator implements Loggable {

    public String getPrefix(String name, byte[] payload) {

        int a = Arrays.hashCode(StringUtils.getBytesUtf8(name));
        int b = Arrays.hashCode(payload);

        String prefix = String.format("%x-%x", a, b);

        logger().debug("Generated {} for '{}' and {} bytes of data", prefix, name, payload.length);

        return prefix;
    }
}
