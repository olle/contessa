package com.studiomediatech.contessa.validation.amqp;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;


public class MissingFilenameHeaderValidationError extends AmqpRejectAndDontRequeueException {

    public MissingFilenameHeaderValidationError(String message) {

        super(message);
    }
}
