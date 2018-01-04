package com.studiomediatech.contessa.ui.web;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ContessaWebController {

    @GetMapping("/{name:.+}")
    public ResponseEntity<byte[]> getContent(@PathVariable("name") String name) {

        System.out.println("Requested " + name);

        return null;
    }
}
