package com.studiomediatech.content.store.api;

import com.studiomediatech.content.store.Loggable;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;


/**
 * The main index controller, providing an intuitive overview of the public API, with easy to use links.
 */
@RestController
public class ApiController implements Loggable {

    ConcurrentHashMap<Boolean, String> baseUrlMap = new ConcurrentHashMap<>(1);

    @GetMapping("/")
    public Map<String, Object> api(HttpServletRequest request) {

        // TODO: Move this to a request interceptor and provide as a "linker" component
        if (baseUrlMap.isEmpty()) {
            logger().info("|> First request detected, need to parse root URL, this is just going to hurt a little..");

            String url = request.getRequestURL().toString();
            String path = request.getServletPath();
            baseUrlMap.putIfAbsent(Boolean.TRUE, url.substring(0, url.length() - path.length()));
            logger().info("|> All done, that wasn't so bad, now was it. We're serving from {}",
                baseUrlMap.get(Boolean.TRUE));
        }

        // TODO: Configuration properties for app, builder info pom-info etc.
        Map<String, Object> api = new HashMap<>();
        api.put("version", "v1");

        return api;
    }
}
