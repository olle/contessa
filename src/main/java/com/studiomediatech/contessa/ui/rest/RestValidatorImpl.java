package com.studiomediatech.contessa.ui.rest;

import com.studiomediatech.contessa.validation.ValidationService;


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
