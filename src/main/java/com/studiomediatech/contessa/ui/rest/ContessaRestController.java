package com.studiomediatech.contessa.ui.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.ui.HttpValidator;
import com.studiomediatech.contessa.ui.ContentRequest;
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

import java.util.HashMap;
import java.util.Map;


@RestController
public class ContessaRestController implements Loggable {

    private final HttpValidator validator;

    private final UiHandler handler;

    public ContessaRestController(HttpValidator validator, UiHandler handler) {

        this.validator = validator;
        this.handler = handler;
    }

    @PostMapping(path = "/api/v1/{name:.+}", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> handleContentUploadOctetStream(@PathVariable("name") String filename,
        @RequestBody byte[] payload) {

        validator.validateForUpload(filename, payload);

        Entry content = handler.handle(UploadRequest.valueOf(filename, payload));

        Map<String, Object> result = toMap(content);
        ResponseEntity<String> response = toJsonResponse(result);

        return response;
    }


    @GetMapping(path = "/api/v1/{identifier}")
    public Map<String, Object> handleContentRequest(@PathVariable("identifier") String identifier) {

        validator.validateRequestedIdentifier(identifier);

        return handler.handle(ContentRequest.forIdentifer(identifier)).map(this::toMap).orElseThrow(() ->
                    new UnknownContentEntryException());
    }


    private Map<String, Object> toMap(Entry content) {

        Map<String, Object> result = new HashMap<>();
        result.put("identifier", content.getId());
        result.put("type", content.getType());
        result.put("suffix", content.getSuffix());

        return result;
    }


    private ResponseEntity<String> toJsonResponse(Map<String, Object> result) {

        try {
            String json = new ObjectMapper().writeValueAsString(result);

            return ResponseEntity.ok().header("Content-Type", MediaType.APPLICATION_JSON_VALUE).body(json);
        } catch (JsonProcessingException e) {
            throw new ContentConversionException(e);
        }
    }
}
