package com.studiomediatech.contessa.validation;

import org.junit.Test;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;


public class ValidationServiceImplTest {

    @Test(expected = MissingFilenameSuffixValidationError.class)
    public void ensureValidatesFilename() throws Exception {

        new ValidationServiceImpl().validFilename("nope");
    }


    @Test(expected = PayloadTooSmallValidationError.class)
    public void ensureValidatesPayload() throws Exception {

        new ValidationServiceImpl().validPayload("toosmall".getBytes());
    }


    @Test(expected = MissingReplyToValidationError.class)
    public void ensureValidatesReplyTo() {

        Message message = MessageBuilder.withBody("nop".getBytes()).build();
        new ValidationServiceImpl().validReplyTo(message);
    }


    @Test(expected = MissingFilenameHeaderValidationError.class)
    public void ensureValidatesFilenameHeaderField() throws Exception {

        Message message = MessageBuilder.withBody("nop".getBytes()).setReplyTo("here/here").build();
        new ValidationServiceImpl().validFilename(message);
    }


    @Test(expected = InvalidIdentifierValidationError.class)
    public void ensureValidatesNullIdentifier() throws Exception {

        new ValidationServiceImpl().validIdentifier(null);
    }


    @Test(expected = InvalidIdentifierValidationError.class)
    public void ensureValidatesEmptyIdentifier() throws Exception {

        new ValidationServiceImpl().validIdentifier("   ");
    }
}
