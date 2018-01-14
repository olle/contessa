package com.studiomediatech.contessa.contents.media;

import com.studiomediatech.contessa.logging.Loggable;

import org.springframework.http.MediaType;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.net.URLConnection;

import java.util.Optional;


/**
 * Wraps mime-type parsing of a byte array into a nifty little parser, that does just-in-time parsing from the given
 * byte array, without using intermediary files - or a file system.
 */
public class MimeTypeParser implements Loggable {

    public String parseMimeType(byte[] source, String suffix) {

        try(ByteArrayInputStream bin = new ByteArrayInputStream(source);
                BufferedInputStream buf = new BufferedInputStream(bin)) {
            return Optional.ofNullable(URLConnection.guessContentTypeFromStream(buf)).orElseGet(() ->
                        mostSuitableMimeTypeForSuffix(suffix));
        } catch (IOException e) {
            logger().warn(e.getMessage());

            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }


    private String mostSuitableMimeTypeForSuffix(String suffix) {

        if ("css".equalsIgnoreCase(suffix)) {
            return "text/css";
        } else if ("txt".equalsIgnoreCase(suffix)) {
            return MediaType.TEXT_PLAIN_VALUE;
        } else if ("html".equalsIgnoreCase(suffix) || "htm".equalsIgnoreCase(suffix)) {
            return MediaType.TEXT_HTML_VALUE;
        } else if ("xml".equalsIgnoreCase(suffix)) {
            return MediaType.TEXT_XML_VALUE;
        } else if ("md".equalsIgnoreCase(suffix)) {
            return MediaType.TEXT_MARKDOWN_VALUE;
        } else if ("pdf".equalsIgnoreCase(suffix)) {
            return MediaType.APPLICATION_PDF_VALUE;
        } else if ("json".equalsIgnoreCase(suffix)) {
            return MediaType.APPLICATION_JSON_VALUE;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }
}
