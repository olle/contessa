package com.studiomediatech.contessa.storage;

import org.junit.Test;

import static org.junit.Assert.assertNull;


public class ContentsBackendNullImplTest {

    @Test
    public void ensureDoesNothingAndExpectingNoFailures() {

        ContentsBackendNullImpl backend = new ContentsBackendNullImpl();

        backend.store("id", "type", "name", new byte[0]);

        assertNull("Wrong results", backend.load("id"));
    }
}
