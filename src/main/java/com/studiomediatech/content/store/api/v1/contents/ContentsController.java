package com.studiomediatech.content.store.api.v1.contents;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ContentsController {

    @PostMapping("/api/v1/contents/{name}")
    public void addMediaContent(byte[] payload) {
    }
}
