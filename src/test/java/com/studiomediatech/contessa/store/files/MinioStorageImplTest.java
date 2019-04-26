/*
 * J.Arrasz
 */
package com.studiomediatech.contessa.store.files;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.store.Storage;
import com.studiomediatech.contessa.store.ContessaException;
import com.studiomediatech.contessa.store.minio.ContessaMinioProperties;
import com.studiomediatech.contessa.store.minio.MinioStorageImpl;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for all relevant interactions with MinioClient.
 *
 * @author Joachim Arrasz <arrasz@synyx.de>
 */
public class MinioStorageImplTest {

    private static final String TEST_BUCKET = "contessa";
    private static final String ENDPOINT_URL = "http://172.17.0.2:9000";
    private static final String SECRET = "password";
    private static final String ACCESS_KEY = "admin";

    @Test
    public void writeFileToStore() {
        Entry reference = createReferenceData();
        Storage store = createMinioStoreImpl();

        store.store(reference);
    }

    @Test
    public void readFileFromStore() {
        Entry reference = createReferenceData();
        Storage store = createMinioStoreImpl();

        assertNotNull(store.retrieve(reference.getId()));

    }

    @Test
    public void fileExists() {

        Entry reference = createReferenceData();

        Storage store = createMinioStoreImpl();

        try {

            store.exists(reference, TEST_BUCKET);
        } catch (ContessaException ex) {
            assertNull(ex);
        }
    }

    private Storage createMinioStoreImpl() {
        ContessaMinioProperties props = new ContessaMinioProperties();
        props.setAccessKey(ACCESS_KEY);
        props.setSecretKey(SECRET);
        props.setEndpoint(ENDPOINT_URL);
        Storage store = new MinioStorageImpl(props, new ObjectMapper());
        return store;
    }

    @Test
    public void deleteFileFromStore() {
        Entry reference = createReferenceData();
        Storage store = createMinioStoreImpl();

        try {
            store.remove(reference, null);
        } catch (ContessaException ex) {
            assertNull(ex);
        }
    }

    private Entry createReferenceData() {
        //create reference data
        Entry reference = new Entry();
        reference.setId("reference");
        reference.setSuffix("txt");
        reference.setType("text/txt");
        reference.setData("lorem Ipsum test data".getBytes());
        return reference;
    }
}
