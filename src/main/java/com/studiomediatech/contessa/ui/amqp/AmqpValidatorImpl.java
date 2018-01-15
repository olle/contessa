package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.validation.ValidationService;

import org.springframework.amqp.core.Message;


public class AmqpValidatorImpl implements AmqpValidator {

    private final ValidationService validationService;

    public AmqpValidatorImpl(ValidationService validationService) {

        this.validationService = validationService;
    }

    @Override
    public void validateUpload(Message message) {

        validationService.validReplyTo(message);
        validationService.validFilename(message);
        validationService.validFilename((String) message.getMessageProperties().getHeaders().get("filename"));
    }
}
