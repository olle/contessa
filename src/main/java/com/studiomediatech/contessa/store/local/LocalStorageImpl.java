package com.studiomediatech.contessa.store.local;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.studiomediatech.contessa.app.autoconfigure.ContessaProperties;
import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.store.Storage;

import org.springframework.stereotype.Component;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Optional;


@Component
public class LocalStorageImpl implements Storage, Loggable {

    private final ContessaProperties props;
    private final ObjectMapper objectMapper;

    public LocalStorageImpl(ContessaProperties props, ObjectMapper objectMapper) {

        this.props = props;
        this.objectMapper = objectMapper;
    }

    @Override
    public void store(Entry entry) {

        String filename = String.format("%s.json", entry.getId());
        Path path = Paths.get(props.getBaseDir(), filename);

        LocalEntry e = toLocalentry(entry);

        try {
            objectMapper.writeValue(path.toFile(), e);
        } catch (IOException ex) {
            throw new LocalStoreFailedException("Unable to store entry: " + filename, ex);
        }
    }


    private LocalEntry toLocalentry(Entry entry) {

        return LocalEntry.valueOf(entry);
    }


    @Override
    public Optional<Entry> retrieve(String identifier) {

        String filename = String.format("%s.json", identifier);
        Path path = Paths.get(props.getBaseDir(), filename);

        try {
            LocalEntry entry = objectMapper.readValue(path.toFile(), LocalEntry.class);

            return Optional.of(entry).map(LocalEntry::asEntry);
        } catch (IOException ex) {
            throw new LocalStoreFailedException("Unable to retrieve entry: " + filename, ex);
        }
    }
}
