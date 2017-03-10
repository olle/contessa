package com.studiomediatech.contessa.api.v1;

import com.studiomediatech.contessa.Loggable;
import com.studiomediatech.contessa.contents.ContentsService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Provides endpoints for handling contents.
 *
 * <p>Basically fails fast or delegates on to the contents service.</p>
 */
@Controller
public class ContentsController implements Loggable {

    private final ContentsService contentsService;

    @Autowired
    public ContentsController(ContentsService contentsService) {

        this.contentsService = contentsService;
    }

    @PostMapping("/api/v1/contents/{name:.+}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMediaContent(@PathVariable("name") String name, @RequestBody byte[] payload) {

        logger().info("Received contents file '{}' with payload of {} bytes", name, payload.length);

        contentsService.addMediaContent(name, payload);

        logger().debug("Done handling content");
    }
}
