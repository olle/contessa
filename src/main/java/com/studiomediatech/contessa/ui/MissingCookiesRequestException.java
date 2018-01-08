package com.studiomediatech.contessa.ui;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@ResponseStatus(HttpStatus.FORBIDDEN)
public class MissingCookiesRequestException extends RuntimeException {

    public MissingCookiesRequestException(List<String> cookies) {

        super(String.join(", ", cookies));
    }
}
