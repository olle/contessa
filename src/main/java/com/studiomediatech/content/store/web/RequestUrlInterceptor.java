package com.studiomediatech.content.store.web;

import com.studiomediatech.content.store.Loggable;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RequestUrlInterceptor extends HandlerInterceptorAdapter implements Loggable {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        logger().debug("|> Intercepting request with pre-handle");

        return super.preHandle(request, response, handler);
    }
}
