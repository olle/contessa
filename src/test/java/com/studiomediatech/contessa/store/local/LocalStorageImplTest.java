package com.studiomediatech.contessa.store.local;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.studiomediatech.contessa.app.autoconfigure.ContessaProperties;
import com.studiomediatech.contessa.domain.Entry;

import org.junit.Test;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertTrue;


public class LocalStorageImplTest {

    @Test
    public void ensureStoresIndexAndContentFiles() throws Exception {

        Path basePath = FileSystems.getDefault().getPath("target/").toAbsolutePath();
        Path contentFile = basePath.resolve("some-id.json");

        try {
            ContessaProperties props = new ContessaProperties();
            props.setBaseDir(basePath.toString());

            LocalStorageImpl sut = new LocalStorageImpl(props, new ObjectMapper());

            Entry entry = new Entry();

            entry.setId("some-id");
            entry.setSuffix("gif");
            entry.setType("image/gif");
            entry.setData("data".getBytes());

            sut.store(entry);

            assertTrue("Missing content file: " + contentFile, Files.exists(contentFile));
        } finally {
            Files.deleteIfExists(contentFile);
        }
    }
}
