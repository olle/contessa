package com.studiomediatech.contessa.ui.rest;

import com.studiomediatech.contessa.ui.Upload;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ConverterTest {

    @Test
    public void ensureConverts() {

        byte[] payload = new byte[] { 1, 2, 3 };
        Upload data = new Converter().convertToUpload("some-filename", payload);

        assertNotNull("Missing data", data);
        assertEquals("Wrong filename", "some-filename", data.filename);
        assertEquals("Wrong payload", payload, data.payload);
    }
}
