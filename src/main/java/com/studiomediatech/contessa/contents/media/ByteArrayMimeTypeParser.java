package com.studiomediatech.contessa.contents.media;

import com.studiomediatech.contessa.logging.Loggable;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.net.URLConnection;

import java.util.Optional;


/**
 * Wraps mime-type parsing of a byte array into a nifty little parser, that does just-in-time parsing from the given
 * byte array, without using intermediary files - or a file system.
 */
public class ByteArrayMimeTypeParser implements Loggable {

    public String parseMimeType(byte[] source) {

        try(ByteArrayInputStream bin = new ByteArrayInputStream(source);
                BufferedInputStream buf = new BufferedInputStream(bin)) {
            return Optional.ofNullable(URLConnection.guessContentTypeFromStream(buf))
                .orElse("application/octet-stream");
        } catch (IOException e) {
            logger().warn(e.getMessage());

            return "application/octet-stream";
        }
    }
}
