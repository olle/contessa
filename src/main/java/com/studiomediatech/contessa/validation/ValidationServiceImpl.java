package com.studiomediatech.contessa.validation;

import com.studiomediatech.contessa.logging.Loggable;

import org.springframework.amqp.core.Message;

import org.springframework.util.StringUtils;

import java.util.Objects;


public class ValidationServiceImpl implements ValidationService, Loggable {

    @Override
    public void validFilename(String filename) {

        if (filename.indexOf('.') == -1) {
            throw new MissingFilenameSuffixValidationError(filename);
        }
    }


    @Override
    public void validPayload(byte[] payload) {

        if (payload.length < 26) {
            throw new PayloadTooSmallValidationError(payload.length);
        }
    }


    @Override
    public void validReplyTo(Message message) {

        if (Objects.isNull(message.getMessageProperties().getReplyTo())) {
            throw new MissingReplyToValidationError("No reply-to set on the upload message.");
        }
    }


    @Override
    public void validFilename(Message message) {

        if (Objects.isNull(message.getMessageProperties().getHeaders().get("filename"))) {
            throw new MissingFilenameHeaderValidationError("No filename header in upload message.");
        }
    }


    @Override
    public void validIdentifier(String identifier) {

        if (!StringUtils.hasText(identifier)) {
            throw new InvalidIdentifierValidationError("Identifier cannot be empty");
        }
    }


    @Override
    public void validName(String name) {

        validIdentifier(name);
        validFilename(name);
    }
}
