package com.studiomediatech.contessa.ui.dropbox;

import com.studiomediatech.contessa.validation.ValidationService;

import org.springframework.stereotype.Component;


@Component
public class DirValidatorImpl implements DirValidator {

    private final ValidationService validationService;

    public DirValidatorImpl(ValidationService validationService) {

        this.validationService = validationService;
    }

    @Override
    public void validateDroppedContent(String filename, byte[] payload) {

        validationService.validFilename(filename);
        validationService.validPayload(payload);
    }
}
