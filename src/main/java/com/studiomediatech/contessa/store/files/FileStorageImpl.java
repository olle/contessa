package com.studiomediatech.contessa.store.files;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.store.Storage;

import org.springframework.stereotype.Component;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Optional;


@Component
public class FileStorageImpl implements Storage, Loggable {

    private final ContessaFilesProperties props;
    private final ObjectMapper objectMapper;

    public FileStorageImpl(ContessaFilesProperties props, ObjectMapper objectMapper) {

        this.props = props;
        this.objectMapper = objectMapper;
    }

    @Override
    public void store(Entry entry) {

        String filename = String.format("%s.json", entry.getId());
        Path path = Paths.get(props.getBaseDir(), filename);

        FileEntry e = toLocalentry(entry);

        try {
            objectMapper.writeValue(path.toFile(), e);
        } catch (IOException ex) {
            throw new FileStoreFailedException("Unable to store entry: " + filename, ex);
        }
    }


    private FileEntry toLocalentry(Entry entry) {

        return FileEntry.valueOf(entry);
    }


    @Override
    public Optional<Entry> retrieve(String identifier) {

        String filename = String.format("%s.json", identifier);
        Path path = Paths.get(props.getBaseDir(), filename);

        try {
            FileEntry entry = objectMapper.readValue(path.toFile(), FileEntry.class);

            return Optional.of(entry).map(FileEntry::asEntry);
        } catch (IOException ex) {
            throw new FileStoreFailedException("Unable to retrieve entry: " + filename, ex);
        }
    }
}
