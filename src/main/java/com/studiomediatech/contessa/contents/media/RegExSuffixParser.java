package com.studiomediatech.contessa.contents.media;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Media type string parser, a small capable tool to use when trying to find out the sort of media type we're dealing
 * with. Simple, in this case, also mean - narrowly cut i.e. not so universally usable. It can handle a small set of
 * media file types (names). Deal with it.
 */
public final class RegExSuffixParser {

    private static final Pattern SUFFIXES = Pattern.compile("^[^.]+\\.(png|gif|jpg|svg|ico|js|css|woff|txt|html|pdf)$",
            Pattern.CASE_INSENSITIVE);

    /**
     * Returns the media file suffix, matching any out of a small set of supported types.
     *
     * @param  source  file name to parse for a known (limited) set of suffixes
     *
     * @return  the string suffix, never empty or {@code null}
     *
     * @throws  UnsupportedMediaTypeException  in case no match could be found
     */
    public String parseSuffix(String source) {

        Matcher matcher = SUFFIXES.matcher(source);

        if (matcher.matches()) {
            return matcher.group(1);
        }

        throw new UnsupportedMediaTypeException(String.format("Could not parse suffix for '%s'", source));
    }


    /**
     * Returns all but the parsed suffix, excluding any dot (.) separator, from the given source string.
     *
     * @param  source  file name to parse
     *
     * @return  the source string prefix, never empty or {@code null}
     *
     * @throws  UnsupportedMediaTypeException  in case of a bad media type (suffix) type
     */
    public String parsePrefix(String source) {

        return source.replace("." + parseSuffix(source), "");
    }
}
