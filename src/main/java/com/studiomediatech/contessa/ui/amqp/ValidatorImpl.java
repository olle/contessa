package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.validation.ValidationService;

import org.springframework.amqp.core.Message;


public class ValidatorImpl {

    private final ValidationService validationService;

    public ValidatorImpl(ValidationService validationService) {

        this.validationService = validationService;
    }

    public void validateUpload(Message message) {

        validationService.validReplyTo(message);
        validationService.validFilename(message);
        validationService.validFilename((String) message.getMessageProperties().getHeaders().get("filename"));
        validationService.validPayload(message.getBody());
    }
}
