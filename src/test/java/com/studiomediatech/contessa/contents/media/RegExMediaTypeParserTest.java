package com.studiomediatech.contessa.contents.media;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class RegExMediaTypeParserTest {

    @Test
    public void ensureFindsSimpleSuffixFromNameWithKnownMediaFormats() throws Exception {

        RegExSuffixParser parser = new RegExSuffixParser();

        assertEquals("Wrong suffix", "png", parser.getSuffix("foo.png"));
        assertEquals("Wrong suffix", "svg", parser.getSuffix("foo.svg"));
        assertEquals("Wrong suffix", "jpg", parser.getSuffix("foo.jpg"));
        assertEquals("Wrong suffix", "gif", parser.getSuffix("foo.gif"));
        assertEquals("Wrong suffix", "ico", parser.getSuffix("foo.ico"));
        assertEquals("Wrong suffix", "css", parser.getSuffix("foo.css"));
        assertEquals("Wrong suffix", "js", parser.getSuffix("foo.js"));
        assertEquals("Wrong suffix", "woff", parser.getSuffix("foo.woff"));
        assertEquals("Wrong suffix", "txt", parser.getSuffix("foo.txt"));
        assertEquals("Wrong suffix", "html", parser.getSuffix("foo.html"));
        assertEquals("Wrong suffix", "pdf", parser.getSuffix("foo.pdf"));
    }


    @Test(expected = UnsupportedMediaTypeException.class)
    public void ensureThrowsForUnknownSuffix() throws Exception {

        new RegExSuffixParser().getSuffix("foo.bar");
    }


    @Test
    public void ensureReturnsPrefixOnly() throws Exception {

        RegExSuffixParser parser = new RegExSuffixParser();

        assertEquals("Wrong prefix", "foo", parser.getPrefix("foo.png"));
        assertEquals("Wrong prefix", "foo", parser.getPrefix("foo.svg"));
        assertEquals("Wrong prefix", "foo", parser.getPrefix("foo.jpg"));
        assertEquals("Wrong prefix", "foo", parser.getPrefix("foo.gif"));
        assertEquals("Wrong prefix", "foo", parser.getPrefix("foo.ico"));
        assertEquals("Wrong prefix", "foo", parser.getPrefix("foo.css"));
        assertEquals("Wrong prefix", "foo", parser.getPrefix("foo.js"));
        assertEquals("Wrong prefix", "foo", parser.getPrefix("foo.woff"));
        assertEquals("Wrong prefix", "foo", parser.getPrefix("foo.txt"));
        assertEquals("Wrong prefix", "foo", parser.getPrefix("foo.html"));
        assertEquals("Wrong prefix", "foo", parser.getPrefix("foo.pdf"));
    }


    @Test(expected = UnsupportedMediaTypeException.class)
    public void ensureThrowsForUnknownPrefix() throws Exception {

        new RegExSuffixParser().getPrefix("foo.bar");
    }
}
