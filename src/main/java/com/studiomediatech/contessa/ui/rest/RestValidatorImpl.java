package com.studiomediatech.contessa.ui.rest;

import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.validation.ValidationService;


final class RestValidatorImpl implements RestValidator {

    private final ValidationService validationService;

    public RestValidatorImpl(ValidationService validationService) {

        this.validationService = validationService;
    }

    @Override
    public void validateUpload(String filename, byte[] payload) {

        validationService.validFilename(filename);
        validationService.validPayload(payload);
    }


    @Override
    public void validateRequest(String identifier) {

        validationService.validIdentifier(identifier);
    }
}

interface RestValidator extends Loggable {


    default void validateUpload(String filename, byte[] payload) {

        logger().warn("Not validating any of {} or {}", filename, payload);
    }


    default void validateRequest(String identifier) {

        logger().warn("Not validating request for {}", identifier);
    }
}
