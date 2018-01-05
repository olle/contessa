package com.studiomediatech.contessa.ui.rest;

import org.junit.Test;

import com.studiomediatech.contessa.ui.UploadCommand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class RestConverterTest {

    @Test
    public void ensureConvertsCorrect() {

        byte[] payload = "payload".getBytes();
        UploadCommand data = new RestConverterImpl().convertToUploadCommand("some-filename", payload);

        assertNotNull("Missing data", data);
        assertEquals("Wrong filename", "some-filename", data.filename);
        assertEquals("Wrong payload", payload, data.payload);
    }
}
