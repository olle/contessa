package com.studiomediatech.contessa.ui.rest;

import com.studiomediatech.contessa.validation.ValidationService;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class ValidatorImplTest {

    @Mock
    ValidationService validationService;

    @InjectMocks
    RestValidatorImpl sut;

    @Test
    public void ensureValidatesFilenameAndPayload() {

        byte[] payload = "payload".getBytes();
        String filename = "filename";

        sut.validateUpload(filename, payload);

        verify(validationService).validFilename(filename);
        verify(validationService).validPayload(payload);
    }
}
