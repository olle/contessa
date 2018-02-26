package com.studiomediatech.contessa.store.files;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.studiomediatech.contessa.app.autoconfigure.ContessaProperties;
import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.store.file.FileStorageImpl;

import org.junit.Test;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Objects;

import static org.junit.Assert.assertTrue;


public class FileStorageImplTest {

    @Test
    public void ensureStoresIndexAndContentFiles() throws Exception {

        Path basePath = FileSystems.getDefault().getPath("target/").toAbsolutePath();

        Path contentFile = null;

        try {
            ContessaProperties props = new ContessaProperties();
            props.setBaseDir(basePath.toString());

            FileStorageImpl sut = new FileStorageImpl(props, new ObjectMapper());

            Entry entry = new Entry();

            entry.setId("some-id");
            entry.setSuffix("gif");
            entry.setType("image/gif");
            entry.setData("data".getBytes());

            sut.store(entry);

            contentFile = basePath.resolve("storage").resolve("some-id.json");
            assertTrue("Missing content file: " + contentFile, Files.exists(contentFile));
        } finally {
            if (Objects.nonNull(contentFile)) {
                Files.deleteIfExists(contentFile);
            }
        }
    }
}
