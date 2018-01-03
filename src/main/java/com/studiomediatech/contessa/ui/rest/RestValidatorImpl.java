package com.studiomediatech.contessa.ui.rest;

import org.springframework.stereotype.Component;

import com.studiomediatech.contessa.validation.ValidationService;

@Component
public final class RestValidatorImpl implements RestValidator {

    private final ValidationService validationService;

    public RestValidatorImpl(ValidationService validationService) {

        this.validationService = validationService;
    }

    @Override
    public void validateUpload(String filename, byte[] payload) {

        validationService.validFilename(filename);
        validationService.validPayload(payload);
    }
}

interface RestValidator {


    default void validateUpload(String filename, byte[] payload) {

        // OK
    }
}
