package com.studiomediatech.contessa.ui.rest;

import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.ui.Data;
import com.studiomediatech.contessa.ui.Service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * REST controller that handles content upload requests.
 */
@RestController
public class ContentUploadController implements Loggable {

    private Converter converter;
    private Service service;
    private Builder builder;

    public ContentUploadController(Converter converter, Service service, Builder builder) {

        this.converter = converter;
        this.service = service;
        this.builder = builder;
    }

    @PostMapping(path = "/api/v1/contents/{name:.+}")
    public Object handleContentUpload(@PathVariable("name") String filename, @RequestBody byte[] payload) {

        return builder.buildUploadResult(service.handleUploadData(converter.convertToUploadData(filename, payload)));
    }
}
