package com.studiomediatech.content.store.web;

import com.studiomediatech.content.store.Loggable;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Custom request handler, ensuring the link helper intercepts any request before handling.
 */
@Component
public class RequestUrlInterceptor extends HandlerInterceptorAdapter implements Loggable {

    private final LinkHelper linkHelper;

    @Autowired
    public RequestUrlInterceptor(LinkHelper linkHelper) {

        this.linkHelper = linkHelper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        logger().trace("|> Intercepting request with pre-handle. Req/Resp/Handler: {} {} {}", request, response,
            handler);

        linkHelper.setRootUrl(request);

        return true;
    }
}
