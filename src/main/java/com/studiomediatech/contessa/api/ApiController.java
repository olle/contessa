package com.studiomediatech.contessa.api;

import com.studiomediatech.contessa.Loggable;
import com.studiomediatech.contessa.http.LinkHelper;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * The main index controller, providing an intuitive overview of the public API, with easy to use links.
 */
@RestController
public class ApiController implements Loggable {

    private final LinkHelper linkHelper;

    @Autowired
    public ApiController(LinkHelper linkHelper) {

        this.linkHelper = linkHelper;
    }

    @GetMapping("/")
    public Map<String, Object> api(HttpServletRequest request) {

        // TODO: Configuration properties for app, builder info pom-info etc.
        Map<String, Object> api = new HashMap<>();
        api.put("version", "v1");
        api.put("self", linkHelper.getRootUrl());

        return api;
    }
}
