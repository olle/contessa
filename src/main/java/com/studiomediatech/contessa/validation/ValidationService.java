package com.studiomediatech.contessa.validation;

import org.springframework.amqp.core.Message;


public interface ValidationService {

    void validFilename(String filename);


    void validPayload(byte[] payload);


    void validReplyTo(Message message);


    void validFilename(Message message);


    void validIdentifier(String identifier);
}
