package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.validation.MissingReplyToValidationError;

import org.springframework.amqp.core.Message;

import java.util.Objects;


public class ValidatorImpl {

    public void validateUpload(Message message) {

        if (Objects.isNull(message.getMessageProperties().getReplyTo())) {
            throw new MissingReplyToValidationError("No reply-to set on the upload message.");
        }
    }
}
