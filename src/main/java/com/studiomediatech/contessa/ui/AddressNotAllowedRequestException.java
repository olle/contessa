package com.studiomediatech.contessa.ui;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.FORBIDDEN)
public class AddressNotAllowedRequestException extends RuntimeException {

    public AddressNotAllowedRequestException(String remoteAddr) {

        super(remoteAddr);
    }
}
