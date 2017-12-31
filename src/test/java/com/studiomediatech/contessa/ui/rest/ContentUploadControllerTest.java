package com.studiomediatech.contessa.ui.rest;

import com.studiomediatech.contessa.ui.TestHandler;
import com.studiomediatech.contessa.validation.MissingFilenameSuffixValidationError;
import com.studiomediatech.contessa.validation.PayloadTooSmallValidationError;

import org.apache.tomcat.util.codec.binary.Base64;

import org.junit.Before;
import org.junit.Test;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertTrue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ContentUploadControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setup() {

        Validator validator = new Validator();
        Converter converter = new Converter();
        TestHandler handler = new TestHandler();
        Builder builder = new Builder();

        ContentUploadController sut = new ContentUploadController(validator, converter, handler, builder);

        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }


    @Test
    public void ensureClientErrorForMissingFileSuffix() throws Exception {

        mockMvc.perform(post("/api/v1/missingsuffix").content("nop".getBytes()))
            .andDo(handler -> {
                    assertTrue("Wrong exception",
                        handler.getResolvedException() instanceof MissingFilenameSuffixValidationError);
                })
            .andExpect(status().is4xxClientError());
    }


    @Test
    public void ensureClientErrorForTooSmallPayload() throws Exception {

        mockMvc.perform(post("/api/v1/image.gif").content("toosmall".getBytes()))
            .andDo(handler -> {
                    assertTrue("Wrong exception",
                        handler.getResolvedException() instanceof PayloadTooSmallValidationError);
                })
            .andExpect(status().is4xxClientError());
    }


    @Test
    public void ensureHandlesSmallesGifPossibleCorrect() throws Exception {

        byte[] payload = Base64.decodeBase64("R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs=");

        mockMvc.perform(post("/api/v1/tiny.gif").content(payload))
            .andExpect(status().isOk())
            .andExpect(content().json("{identifier: 'test-tiny.gif'}"));
    }
}
