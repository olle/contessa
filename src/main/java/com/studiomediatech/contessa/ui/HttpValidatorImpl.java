package com.studiomediatech.contessa.ui;

import com.studiomediatech.contessa.validation.ValidationService;


/**
 * A validation component, common for both the Web and RESTful UIs.
 */
public class HttpValidatorImpl implements HttpValidator {

    private final ValidationService validationService;

    public HttpValidatorImpl(ValidationService validationService) {

        this.validationService = validationService;
    }

    @Override
    public void validateForUpload(String filename, byte[] payload) {

        validationService.validFilename(filename);
        validationService.validPayload(payload);
    }


    @Override
    public void validateRequestedName(String name) {

        validationService.validName(name);
    }


    @Override
    public void validateRequestedIdentifier(String identifier) {

        validationService.validIdentifier(identifier);
    }
}
