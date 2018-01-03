package com.studiomediatech.contessa.validation;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;


public class MissingReplyToValidationError extends AmqpRejectAndDontRequeueException {

    public MissingReplyToValidationError(String message) {

        super(message);
    }
}
