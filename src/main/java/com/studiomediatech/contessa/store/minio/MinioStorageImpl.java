package com.studiomediatech.contessa.store.minio;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.common.annotations.VisibleForTesting;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.store.Storage;

import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidArgumentException;
import io.minio.errors.InvalidBucketNameException;

import io.minio.errors.MinioException;
import io.minio.errors.NoResponseException;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.event.EventListener;

import org.springframework.stereotype.Component;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

@EnableConfigurationProperties(ContessaMinioProperties.class)
@Component
public class MinioStorageImpl implements Storage {

    private static final String BUCKET = "contessa";
    private static final String META_OBJECT = "meta";

    private final ObjectMapper objectMapper;

    private final AtomicLong count = new AtomicLong();

    @VisibleForTesting
    Supplier<MinioClient> client;

    /**
     * Default Impl for Min.io Servers.
     *
     * @param props configuration of our minio client to connect minio server.
     * @param objectMapper
     */
    public MinioStorageImpl(ContessaMinioProperties props, ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;

        this.client
                = () -> {
                    try {
                        return new MinioClient(props.getEndpoint(), props.getAccessKey(), props.getSecretKey());
                    } catch (MinioException e) {
                        throw new RuntimeException("Failed to initialize Minio client.", e);
                    }
                };
    }

    @EventListener
    public void on(ApplicationReadyEvent _event) {

        ensureBucketIsCreated();
        loadObjectsCount();
        updateObjectsCount();
    }

    private void ensureBucketIsCreated() {

        try {
            if (!client.get().bucketExists(BUCKET)) {
                client.get().makeBucket(BUCKET);
            }
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException | XmlPullParserException e) {
            throw new RuntimeException("Failed to initialize 'contessa' bucket to Minio storage", e);
        }
    }

    private void loadObjectsCount() {

        try {
            client.get().statObject(BUCKET, META_OBJECT);

            InputStream in = client.get().getObject(BUCKET, META_OBJECT);
            MetaEntry entry = objectMapper.readValue(in, MetaEntry.class);
            this.count.set(Optional.ofNullable(entry.count).orElse(0L));
            in.close();
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException | XmlPullParserException e) {
            logger().warn("No object count found, will be initialized to 0");
        }
    }

    private void updateObjectsCount() {

        try {
            long currentCount = this.count.get();
            MetaEntry entry = new MetaEntry();
            entry.count = currentCount;

            byte[] bytes = objectMapper.writeValueAsBytes(entry);
            ByteArrayInputStream bain = new ByteArrayInputStream(bytes);
            client.get().putObject(BUCKET, META_OBJECT, bain, "application/octet-stream");
            bain.close();
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException | XmlPullParserException e) {
            throw new RuntimeException("Failed to write meta entry to Minio storage", e);
        }
    }

    @Override
    public void store(Entry entry) {
        ensureBucketIsCreated();
        try {
            String filename = String.format("%s.json", entry.getId());
            MinioEntry m = MinioEntry.valueOf(entry);

            byte[] bytes = objectMapper.writeValueAsBytes(m);
            ByteArrayInputStream bain = new ByteArrayInputStream(bytes);
            client.get().putObject(BUCKET, filename, bain, "application/octet-stream");
            bain.close();
            this.count.incrementAndGet();
            updateObjectsCount();
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException | XmlPullParserException e) {
            throw new RuntimeException("Failed to write entry to Minio storage", e);
        }
    }

    @Override
    public Optional<Entry> retrieve(String identifier) {

        try {
            String filename = String.format("%s.json", identifier);
            client.get().statObject(BUCKET, filename); // Or throws!

            InputStream in = client.get().getObject(BUCKET, filename);
            MinioEntry entry = objectMapper.readValue(in, MinioEntry.class);
            in.close();

            return Optional.ofNullable(entry.asEntry());
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException | XmlPullParserException e) {
            return Optional.empty();
        }
    }

    @Override
    public long count() {

        return this.count.get();
    }

    @Override
    public boolean exists(Entry entry, String path) throws ContessaException {
        logger().debug("called with path :" + path + " for entry with id: " + entry.getId());
        InputStream stream = null;
        String filename = String.format("%s.json", entry.getId());
            
        try {
            if (path != null) {
                stream = this.client.get().getObject(path, filename);
            } else {
                stream = this.client.get().getObject(BUCKET, filename);
            }
        } catch (InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException | IOException | InvalidKeyException | NoResponseException | XmlPullParserException | ErrorResponseException | InternalException | InvalidArgumentException ex) {
            throw new ContessaException(ex);
        }

        return stream != null;
    }

    @Override
    public void remove(Entry entry, String path) throws ContessaException {
        logger().debug("entry.toString() is being deleted now. Path is given: " + path);
        String filename = String.format("%s.json", entry.getId());
        
        try {
            this.client.get().removeObject(BUCKET, filename);
        } catch (InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException | IOException | InvalidKeyException | NoResponseException | XmlPullParserException | ErrorResponseException | InternalException | InvalidArgumentException ex) {
            logger().error("Unable to remove object. Exception occured: ", ex);
            throw new ContessaException(ex);
        }
    }
}

class MetaEntry {

    public Long count;
}
