package com.studiomediatech.contessa.storage.local;

import com.studiomediatech.contessa.app.autoconfigure.ContessaProperties;
import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.storage.Storage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Optional;


public class LocalStorageImpl implements Storage, Loggable {

    private final ContessaProperties config;

    public LocalStorageImpl(ContessaProperties config) {

        this.config = config;
    }

    String getFileName(String id, String suffix) {

        return String.format("%s.%s", id, suffix);
    }


    @Override
    public void store(Entry entry) {

        String path = config.getBaseDir();
        String name = getFileName(entry.getId(), entry.getSuffix());

        try {
            Path p = Paths.get(path, name);
            Files.write(p, entry.getData());
        } catch (Throwable e) {
            logger().error("Could not store entry", e);
        }
    }


    @Override
    public Optional<Entry> retrieve(String identifier) {

        logger().warn("Not retrieving anything!");

        return Optional.empty();
    }
}
