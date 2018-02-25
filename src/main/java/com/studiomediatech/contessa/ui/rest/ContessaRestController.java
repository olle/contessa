package com.studiomediatech.contessa.ui.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.ui.ContentRequest;
import com.studiomediatech.contessa.ui.HttpValidator;
import com.studiomediatech.contessa.ui.InfoRequest;
import com.studiomediatech.contessa.ui.UiHandler;
import com.studiomediatech.contessa.ui.UnknownContentEntryException;
import com.studiomediatech.contessa.ui.UploadRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


@RestController
public class ContessaRestController implements Loggable {

    private final HttpValidator validator;
    private final UiHandler handler;
    private final ObjectMapper objectMapper;

    public ContessaRestController(HttpValidator validator, UiHandler handler, ObjectMapper objectMapper) {

        this.validator = validator;
        this.handler = handler;

        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @GetMapping(path = "/api/v1")
    public Map<String, Object> handleInfo(HttpServletRequest request) {

        validator.validateCookies(request);

        return handler.handle(InfoRequest.createNew());
    }


    @PostMapping(path = "/api/v1/{name:.+}", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> handleContentUploadOctetStream(@PathVariable("name") String filename,
        @RequestBody byte[] payload, HttpServletRequest request) {

        validator.validateCookies(request);
        validator.validateRemoteAddress(request);
        validator.validateForUpload(filename, payload);

        Entry content = handler.handle(UploadRequest.valueOf(filename, payload));

        return toJsonResponse(toMap(content));
    }


    @GetMapping(path = "/api/v1/{identifier}")
    public Map<String, Object> handleContentRequest(@PathVariable("identifier") String identifier,
        HttpServletRequest request) {

        validator.validateCookies(request);
        validator.validateRequestedIdentifier(identifier);

        return handler.handle(ContentRequest.forIdentifer(identifier)).map(this::toMap).orElseThrow(() ->
                    new UnknownContentEntryException());
    }


    private Map<String, Object> toMap(Entry content) {

        Map<String, Object> result = new HashMap<>();

        result.put("identifier", content.getId());
        result.put("type", content.getType());
        result.put("suffix", content.getSuffix());

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(String.format("%s.%s", content.getId(), content.getSuffix()))
                .build()
                .toUriString();

        result.put("uri", uri);
        result.put("length", content.getData().length);

        return result;
    }


    private ResponseEntity<String> toJsonResponse(Map<String, Object> result) {

        try {
            String json = objectMapper.writeValueAsString(result);

            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json + System.lineSeparator());
        } catch (JsonProcessingException e) {
            throw new ContentConversionException(e);
        }
    }
}
