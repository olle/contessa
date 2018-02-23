package com.studiomediatech.contessa.store.minio;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.common.annotations.VisibleForTesting;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.store.Storage;

import io.minio.MinioClient;

import io.minio.errors.MinioException;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.stereotype.Component;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.Optional;
import java.util.function.Supplier;


@EnableConfigurationProperties(ContessaMinioProperties.class)
@Component
public class MinioStorageImpl implements Storage {

    private static final String BUCKET = "contessa";

    private final ObjectMapper objectMapper;

    @VisibleForTesting
    Supplier<MinioClient> client;

    public MinioStorageImpl(ContessaMinioProperties props, ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;

        this.client =
            () -> {
            try {
                return new MinioClient(props.getEndpoint(), props.getAccessKey(), props.getSecretKey());
            } catch (MinioException e) {
                throw new RuntimeException("Failed to initialize Minio client.", e);
            }
        };
    }

    @Override
    public void store(Entry entry) {

        try {
            MinioClient minioClient = client.get();

            if (!minioClient.bucketExists(BUCKET)) {
                minioClient.makeBucket(BUCKET);
            }

            String filename = String.format("%s.json", entry.getId());
            MinioEntry m = MinioEntry.valueOf(entry);

            ByteArrayInputStream bain = new ByteArrayInputStream(objectMapper.writeValueAsBytes(m));
            minioClient.putObject(BUCKET, filename, bain, "application/json");
            bain.close();
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException | XmlPullParserException e) {
            throw new RuntimeException("Failed to write entry to Minio storage", e);
        }
    }


    @Override
    public Optional<Entry> retrieve(String identifier) {

        try {
            MinioClient minioClient = client.get();
            String filename = String.format("%s.json", identifier);

            minioClient.statObject(BUCKET, filename); // Or throws!

            InputStream in = minioClient.getObject(BUCKET, filename);
            MinioEntry entry = objectMapper.readValue(in, MinioEntry.class);
            in.close();

            return Optional.ofNullable(entry.asEntry());
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException | XmlPullParserException e) {
            return Optional.empty();
        }
    }
}
