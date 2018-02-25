package com.studiomediatech.contessa.ui.web;

import com.studiomediatech.contessa.app.autoconfigure.ContessaProperties;
import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.ui.ContentRequest;
import com.studiomediatech.contessa.ui.HttpValidator;
import com.studiomediatech.contessa.ui.InfoRequest;
import com.studiomediatech.contessa.ui.UiHandler;
import com.studiomediatech.contessa.ui.UnknownContentEntryException;
import com.studiomediatech.contessa.ui.UploadRequest;

import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;

import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ContessaWebController {

    private final HttpValidator validator;
    private final UiHandler handler;
    private final ContessaProperties props;

    public ContessaWebController(HttpValidator validator, UiHandler handler, ContessaProperties props) {

        this.validator = validator;
        this.handler = handler;
        this.props = props;
    }

    @GetMapping(path = "/")
    public ResponseEntity<String> handleInfo(HttpServletRequest request) {

        validator.validateCookies(request);

        Map<String, Object> map = handler.handle(InfoRequest.createNew());

        int len = map.keySet().stream().mapToInt(String::length).max().orElse(10) + 1;

        String info = map.entrySet()
                .stream()
                .map(e -> String.format("%-" + len + "s %s", StringUtils.capitalize(e.getKey() + ":"), e.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));

        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(info);
    }


    @PostMapping(path = "/{name:.+}", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> handleContentUploadOctetStream(@PathVariable("name") String filename,
        @RequestBody byte[] payload, HttpServletRequest request) {

        validator.validateCookies(request);
        validator.validateRemoteAddress(request);
        validator.validateForUpload(filename, payload);

        Entry content = handler.handle(UploadRequest.valueOf(filename, payload));

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(String.format("%s.%s", content.getId(), content.getSuffix()))
                .build()
                .toUriString() + System.lineSeparator();

        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(uri);
    }


    @GetMapping(path = "/{name:.+}")
    public ResponseEntity<byte[]> handleContentRequest(@PathVariable("name") String name, HttpServletRequest request) {

        validator.validateCookies(request);
        validator.validateRequestedName(name);

        Entry entry = handler
                .handle(ContentRequest.forName(name)).orElseThrow(() -> new UnknownContentEntryException());

        return ResponseEntity.ok()
            .contentType(MediaType.valueOf(entry.getType()))
            .contentLength(entry.getData().length)
            .eTag(entry.getId())
            .cacheControl(CacheControl.maxAge(props.getMaxAge(), TimeUnit.SECONDS).cachePublic())
            .body(entry.getData());
    }
}
