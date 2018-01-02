package com.studiomediatech.contessa.ui.rest;

import com.studiomediatech.contessa.validation.MissingFilenameSuffixValidationError;
import com.studiomediatech.contessa.validation.PayloadTooSmallValidationError;


public class ValidatorImpl {

    public void validate(String filename, byte[] payload) {

        if (filename.indexOf('.') == -1) {
            throw new MissingFilenameSuffixValidationError(filename);
        }

        if (payload.length < 26) {
            throw new PayloadTooSmallValidationError(payload.length);
        }
    }
}
