package com.studiomediatech.contessa.ui.web;

import com.studiomediatech.contessa.ui.UiHandler;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ContessaWebController {

    private final WebValidator validator;
    private final WebConverter converter;
    private final UiHandler handler;

    public ContessaWebController(WebValidator validator, UiHandler handler, WebConverter converter) {

        this.validator = validator;
        this.handler = handler;
        this.converter = converter;
    }

    @GetMapping("/{name:.+}")
    public ResponseEntity<byte[]> getContent(@PathVariable("name") String name) {

        validator.validateContentRequest(name);

        return null;
    }
}
