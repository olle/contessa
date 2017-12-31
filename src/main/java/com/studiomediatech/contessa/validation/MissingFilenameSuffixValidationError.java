package com.studiomediatech.contessa.validation;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingFilenameSuffixValidationError extends RuntimeException {

    public MissingFilenameSuffixValidationError(String filename) {

        super(String.format("Missing filename suffix in given filename: '%s'", filename));
    }
}
