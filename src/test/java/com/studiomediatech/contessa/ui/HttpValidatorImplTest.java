package com.studiomediatech.contessa.ui;

import com.studiomediatech.contessa.ui.HttpValidatorImpl;
import com.studiomediatech.contessa.validation.ValidationService;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class HttpValidatorImplTest {

    @Mock
    ValidationService validationService;

    @InjectMocks
    HttpValidatorImpl sut;

    @Test
    public void ensureValidatesFilenameAndPayload() {

        byte[] payload = "payload".getBytes();
        String filename = "filename";

        sut.validateForUpload(filename, payload);

        verify(validationService).validFilename(filename);
        verify(validationService).validPayload(payload);
    }
}
