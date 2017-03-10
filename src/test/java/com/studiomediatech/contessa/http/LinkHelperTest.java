package com.studiomediatech.contessa.http;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.springframework.test.context.junit4.SpringRunner;

import com.studiomediatech.contessa.http.LinkHelper;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class LinkHelperTest {

    @Mock
    HttpServletRequest request;

    @Test
    public void ensureFirstCallToSetRootUrlParsesRootUrlFromRequest() throws Exception {

        when(request.getRequestURL()).thenReturn(new StringBuffer("http://host:port/and/some/path"));
        when(request.getRequestURI()).thenReturn("/and/some/path");

        LinkHelper linkHelper = new LinkHelper();
        assertNull(linkHelper.getRootUrl());

        linkHelper.setRootUrl(request);
        assertEquals("http://host:port", linkHelper.getRootUrl());
    }


    @Test
    public void consecutiveCallsDoesNotCalculateRootUrl() throws Exception {

        when(request.getRequestURL()).thenReturn(new StringBuffer("http://host:port/and/some/path"));
        when(request.getRequestURI()).thenReturn("/and/some/path");

        LinkHelper linkHelper = new LinkHelper();
        linkHelper.setRootUrl(request);
        linkHelper.setRootUrl(request);

        verify(request, times(1)).getRequestURL();
        verify(request, times(1)).getRequestURI();
    }
}
