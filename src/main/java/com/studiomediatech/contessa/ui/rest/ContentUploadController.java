package com.studiomediatech.contessa.ui.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.ui.Handler;
import com.studiomediatech.contessa.ui.Response;
import com.studiomediatech.contessa.ui.Upload;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * REST-ful controller that handles content upload requests.
 */
@RestController
public class ContentUploadController implements Loggable {

    private final RestValidator validator;

    private Converter converter;
    private Handler service;
    private Builder builder;

    public ContentUploadController(RestValidator validator, Converter converter, Handler handler, Builder builder) {

        this.validator = validator;
        this.converter = converter;
        this.service = handler;
        this.builder = builder;
    }

    @PostMapping(path = "/api/v1/{name:.+}")
    public ResponseEntity<String> handleContentUpload(@PathVariable("name") String filename,
        @RequestBody byte[] payload) {

        validator.validateUpload(filename, payload);

        Upload data = converter.convertToUpload(filename, payload);
        String identifier = service.handleUploadData(data);

        Map<String, Object> result = builder.buildUploadResult(identifier);
        Response response = builder.buildUploadResponse(identifier);

        return toJsonResponse(result);
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
