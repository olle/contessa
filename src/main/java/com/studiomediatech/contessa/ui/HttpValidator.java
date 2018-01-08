package com.studiomediatech.contessa.ui;

import javax.servlet.http.HttpServletRequest;


public interface HttpValidator {

    void validateForUpload(String filename, byte[] payload);


    void validateRequestedName(String name);


    void validateRequestedIdentifier(String identifier);


    void validateCookies(HttpServletRequest request);
}
