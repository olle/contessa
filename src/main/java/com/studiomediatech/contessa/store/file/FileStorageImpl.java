package com.studiomediatech.contessa.store.file;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.studiomediatech.contessa.app.autoconfigure.ContessaProperties;
import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.store.Storage;
import com.studiomediatech.contessa.store.minio.ContessaException;

import org.springframework.stereotype.Component;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Optional;


@Component
public class FileStorageImpl implements Storage, Loggable {

    private static final String STORAGE_DIR = "storage";

    private final ContessaProperties props;
    private final ObjectMapper objectMapper;

    public FileStorageImpl(ContessaProperties props, ObjectMapper objectMapper) {

        this.props = props;
        this.objectMapper = objectMapper;
    }

    @Override
    public void store(Entry entry) {

        String filename = String.format("%s.json", entry.getId());
        Path path = getStoragePath().resolve(filename);
        FileEntry e = FileEntry.valueOf(entry);

        try {
            objectMapper.writeValue(path.toFile(), e);
        } catch (IOException ex) {
            throw new FileStoreFailedException("Unable to store entry: " + filename, ex);
        }
    }


    private Path getStoragePath() {

        Path dir = Paths.get(props.getBaseDir(), STORAGE_DIR);

        if (!dir.toFile().exists()) {
            dir.toFile().mkdirs();
        }

        return dir;
    }


    @Override
    public Optional<Entry> retrieve(String identifier) {

        String filename = String.format("%s.json", identifier);
        Path path = getStoragePath().resolve(filename);

        try {
            FileEntry entry = objectMapper.readValue(path.toFile(), FileEntry.class);

            return Optional.of(entry).map(FileEntry::asEntry);
        } catch (IOException ex) {
            throw new FileStoreFailedException("Unable to retrieve entry: " + filename, ex);
        }
    }


    @Override
    public long count() {

        return getStoragePath().toFile().list((dir, name) -> name.contains(".json")).length;
    }

    @Override
    public boolean exists(Entry entry, String path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Entry entry, String hpat) throws ContessaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
