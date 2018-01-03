package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.validation.MissingReplyToValidationError;

import org.junit.Test;

import org.springframework.amqp.core.MessageBuilder;


public class ValidatorImplTest {

    @Test(expected = MissingReplyToValidationError.class)
    public void ensureValidatesReplyTo() {

        new ValidatorImpl().validateUpload(MessageBuilder.withBody("nop".getBytes()).build());
    }
}
