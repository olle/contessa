package com.studiomediatech.contessa.validation;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PayloadTooSmallValidationError extends AmqpRejectAndDontRequeueException {

    public PayloadTooSmallValidationError(int length) {

        super(String.format("Payload too small, got only %d bytes.", length));
    }
}
