package com.studiomediatech.contessa.contents;

import com.studiomediatech.contessa.storage.ContentsBackend;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ContentsServiceImplTest {

    @Mock
    ContentsBackend backend;

    ContentsServiceImpl service;

    @Before
    public void setup() {

        service = new ContentsServiceImpl(backend);
    }


    @Test
    public void ensureDelegatesToStoreGivenContents() throws Exception {

        byte[] fixture = new byte[0];
        service.addMediaContent("foobar.gif", fixture);

        verify(backend).store(anyString(), eq("gif"), eq("foobar.gif"), eq(fixture));
    }


    @Test
    public void ensureFetchesFromBackend() throws Exception {

        byte[] fixture = new byte[0];
        when(backend.load(anyString())).thenReturn(fixture);

        Map<String, Object> results = service.getMediaContent("code.jpg");

        verify(backend).load("code");

        assertEquals("Wrong data", fixture, (byte[]) results.get("image"));
    }
}
