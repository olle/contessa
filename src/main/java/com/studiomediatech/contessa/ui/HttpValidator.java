package com.studiomediatech.contessa.ui;

public interface HttpValidator {

    void validateForUpload(String filename, byte[] payload);


    void validateRequestedName(String name);


    void validateRequestedIdentifier(String identifier);
}
