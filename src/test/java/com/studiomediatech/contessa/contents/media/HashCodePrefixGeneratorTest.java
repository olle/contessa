package com.studiomediatech.contessa.contents.media;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;


public class HashCodePrefixGeneratorTest {

    @Test
    public void ensureDifferentNameSamePayloadAreDifferentPrefix() {

        HashCodePrefixGenerator generator = new HashCodePrefixGenerator();

        String p1 = generator.getPrefix("foo", new byte[] { 1, 2, 3 });
        String p2 = generator.getPrefix("bar", new byte[] { 1, 2, 3 });

        assertNotNull("Must not be null", p1);
        assertNotNull("Must not be null", p2);

        assertNotEquals("Must not be equal", p1, p2);
    }


    @Test
    public void ensureDifferntPayloadSameNameAreDifferentPrefix() throws Exception {

        HashCodePrefixGenerator generator = new HashCodePrefixGenerator();

        String p1 = generator.getPrefix("hello", new byte[] { 1, 2, 3 });
        String p2 = generator.getPrefix("hello", new byte[] { 3, 2, 1 });

        assertNotNull("Must not be null", p1);
        assertNotNull("Must not be null", p2);

        assertNotEquals("Must not be equal", p1, p2);
    }
}
