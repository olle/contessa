package com.studiomediatech.contessa.ui.web;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.ui.ContentRequest;
import com.studiomediatech.contessa.ui.HttpValidator;
import com.studiomediatech.contessa.ui.UiHandler;
import com.studiomediatech.contessa.ui.UnknownContentEntryException;
import com.studiomediatech.contessa.ui.UploadRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Controller
public class ContessaWebController {

    private final HttpValidator validator;
    private final UiHandler handler;

    public ContessaWebController(HttpValidator validator, UiHandler handler) {

        this.validator = validator;
        this.handler = handler;
    }

    @GetMapping(path = "/")
    public String info() {

        return "info";
    }


    @PostMapping(path = "/{name:.+}", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> handleContentUploadOctetStream(@PathVariable("name") String filename,
        @RequestBody byte[] payload) {

        validator.validateForUpload(filename, payload);

        Entry content = handler.handle(UploadRequest.valueOf(filename, payload));

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(String.format("%s.%s", content.getId(), content.getSuffix()))
                .build()
                .toUriString() + System.lineSeparator();

        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(uri);
    }


    @GetMapping(path = "/{name:.+}")
    public ResponseEntity<byte[]> getContent(@PathVariable("name") String name) {

        validator.validateRequestedName(name);

        Entry entry = handler
                .handle(ContentRequest.forName(name)).orElseThrow(() -> new UnknownContentEntryException());

        return ResponseEntity.ok()
            .contentType(MediaType.valueOf(entry.getType()))
            .contentLength(entry.getData().length)
            .eTag(entry.getId())
            .lastModified(42L)
            .header("Cache-Control", "max-age=8640000, public, immutable")
            .body(entry.getData());
    }
}
