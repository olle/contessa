package com.studiomediatech.content.store.web;

import com.studiomediatech.content.store.Loggable;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;


/**
 * Custom helper component, making it easier to create API links.
 */
@Component
public class LinkHelper implements Loggable {

    private final ConcurrentHashMap<Boolean, String> rootUrlMap = new ConcurrentHashMap<>(1);

    void setRootUrl(HttpServletRequest request) {

        if (!rootUrlMap.isEmpty()) {
            logger().debug("|> Not the first request, root URL already set");

            return;
        }

        logger().info("|> First request detected, parsing root URL, this is just going to hurt a little..");

        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();

        rootUrlMap.putIfAbsent(Boolean.TRUE, url.substring(0, url.length() - uri.length()));

        logger().info("|> All done, that wasn't so bad, now was it. We're serving from {}",
            rootUrlMap.get(Boolean.TRUE));
    }


    public String getRootUrl() {

        return rootUrlMap.get(Boolean.TRUE);
    }
}
