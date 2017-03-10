package com.studiomediatech.contessa.contents;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
public class ContentsServiceImplTest {

    @Test
    public void ensureDifferentNameSamePayloadAreDifferentPrefix() {

        ContentsServiceImpl c = new ContentsServiceImpl(null);

        String p1 = c.getPrefix("foo", new byte[] { 1, 2, 3 });
        String p2 = c.getPrefix("bar", new byte[] { 1, 2, 3 });

        assertNotNull("Must not be null", p1);
        assertNotNull("Must not be null", p2);

        assertNotEquals("Must not be equal", p1, p2);
    }


    @Test
    public void ensureDifferntPayloadSameNameAreDifferentPrefix() throws Exception {

        ContentsServiceImpl c = new ContentsServiceImpl(null);

        String p1 = c.getPrefix("hello", new byte[] { 1, 2, 3 });
        String p2 = c.getPrefix("hello", new byte[] { 3, 2, 1 });

        assertNotNull("Must not be null", p1);
        assertNotNull("Must not be null", p2);

        assertNotEquals("Must not be equal", p1, p2);
    }


    @Test
    public void ensureFindsSimpleSuffixFromNameWithKnownMediaFormats() throws Exception {

        ContentsServiceImpl c = new ContentsServiceImpl(null);

        assertEquals("Wrong suffix", "png", c.getSuffix("foo.png"));
        assertEquals("Wrong suffix", "svg", c.getSuffix("foo.svg"));
        assertEquals("Wrong suffix", "jpg", c.getSuffix("foo.jpg"));
        assertEquals("Wrong suffix", "gif", c.getSuffix("foo.gif"));
        assertEquals("Wrong suffix", "ico", c.getSuffix("foo.ico"));
        assertEquals("Wrong suffix", "css", c.getSuffix("foo.css"));
        assertEquals("Wrong suffix", "js", c.getSuffix("foo.js"));
        assertEquals("Wrong suffix", "woff", c.getSuffix("foo.woff"));
        assertEquals("Wrong suffix", "txt", c.getSuffix("foo.txt"));
        assertEquals("Wrong suffix", "html", c.getSuffix("foo.html"));
        assertEquals("Wrong suffix", "pdf", c.getSuffix("foo.pdf"));
    }


    @Test(expected = UnsupportedMediaTypeException.class)
    public void ensureThrowsForUnknownSuffix() throws Exception {

        new ContentsServiceImpl(null).getSuffix("foo.bar");
    }
}
