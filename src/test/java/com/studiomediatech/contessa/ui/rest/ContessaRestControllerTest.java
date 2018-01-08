package com.studiomediatech.contessa.ui.rest;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.ui.ContentRequest;
import com.studiomediatech.contessa.ui.HttpValidator;
import com.studiomediatech.contessa.ui.UiHandler;
import com.studiomediatech.contessa.ui.UploadRequest;

import org.apache.tomcat.util.codec.binary.Base64;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class ContessaRestControllerTest {

    @Mock
    HttpValidator validator;
    @Mock
    UiHandler handler;

    @InjectMocks
    ContessaRestController sut;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }


    @Test
    public void ensureHandlesUpload() throws Exception {

        Entry entry = new Entry();
        entry.setId("some-identifier");
        entry.setData("nop".getBytes());

        when(handler.handle(any(UploadRequest.class))).thenReturn(entry);

        byte[] payload = Base64.decodeBase64("R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs=");

        mockMvc.perform(post("/api/v1/tiny.gif").content(payload))
            .andExpect(status().isOk())
            .andExpect(content().json("{"
                    + "identifier: 'some-identifier',"
                    + "length: 3"
                    + "}"));
    }


    @Test
    public void ensureHandlesContentRequest() throws Exception {

        Entry entry = new Entry();
        entry.setData("nop".getBytes());

        when(handler.handle(any(ContentRequest.class))).thenReturn(Optional.of(entry));

        mockMvc.perform(get("/api/v1/some-identifier"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
