package com.studiomediatech.contessa.contents.media;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class RegExMediaTypeParserTest {

    @Test
    public void ensureFindsSimpleSuffixFromNameWithKnownMediaFormats() throws Exception {

        RegExSuffixParser parser = new RegExSuffixParser();

        assertEquals("Wrong suffix", "png", parser.parseSuffix("foo.png"));
        assertEquals("Wrong suffix", "svg", parser.parseSuffix("foo.svg"));
        assertEquals("Wrong suffix", "jpg", parser.parseSuffix("foo.jpg"));
        assertEquals("Wrong suffix", "gif", parser.parseSuffix("foo.gif"));
        assertEquals("Wrong suffix", "ico", parser.parseSuffix("foo.ico"));
        assertEquals("Wrong suffix", "css", parser.parseSuffix("foo.css"));
        assertEquals("Wrong suffix", "js", parser.parseSuffix("foo.js"));
        assertEquals("Wrong suffix", "woff", parser.parseSuffix("foo.woff"));
        assertEquals("Wrong suffix", "txt", parser.parseSuffix("foo.txt"));
        assertEquals("Wrong suffix", "html", parser.parseSuffix("foo.html"));
        assertEquals("Wrong suffix", "pdf", parser.parseSuffix("foo.pdf"));
    }


    @Test(expected = UnsupportedMediaTypeException.class)
    public void ensureThrowsForUnknownSuffix() throws Exception {

        new RegExSuffixParser().parseSuffix("foo.bar");
    }


    @Test
    public void ensureReturnsPrefixOnly() throws Exception {

        RegExSuffixParser parser = new RegExSuffixParser();

        assertEquals("Wrong prefix", "foo", parser.parsePrefix("foo.png"));
        assertEquals("Wrong prefix", "foo", parser.parsePrefix("foo.svg"));
        assertEquals("Wrong prefix", "foo", parser.parsePrefix("foo.jpg"));
        assertEquals("Wrong prefix", "foo", parser.parsePrefix("foo.gif"));
        assertEquals("Wrong prefix", "foo", parser.parsePrefix("foo.ico"));
        assertEquals("Wrong prefix", "foo", parser.parsePrefix("foo.css"));
        assertEquals("Wrong prefix", "foo", parser.parsePrefix("foo.js"));
        assertEquals("Wrong prefix", "foo", parser.parsePrefix("foo.woff"));
        assertEquals("Wrong prefix", "foo", parser.parsePrefix("foo.txt"));
        assertEquals("Wrong prefix", "foo", parser.parsePrefix("foo.html"));
        assertEquals("Wrong prefix", "foo", parser.parsePrefix("foo.pdf"));
    }


    @Test(expected = UnsupportedMediaTypeException.class)
    public void ensureThrowsForUnknownPrefix() throws Exception {

        new RegExSuffixParser().parsePrefix("foo.bar");
    }
}
