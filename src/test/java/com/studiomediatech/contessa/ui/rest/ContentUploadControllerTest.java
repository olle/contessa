package com.studiomediatech.contessa.ui.rest;

import com.studiomediatech.contessa.ui.UiHandler;

import org.apache.tomcat.util.codec.binary.Base64;

import org.junit.Before;
import org.junit.Test;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ContentUploadControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setup() {

        RestValidator validator = new RestValidator() {
        };

        RestConverter converter = new RestConverterImpl();

        UiHandler handler = new UiHandler() {

            @Override
            public String handleUploadCommand(UploadCommand command) {

                return "some-identifier";
            }
        };

        ContentUploadController sut = new ContentUploadController(validator, converter, handler);

        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }


    @Test
    public void ensureHandlesSmallesGifPossibleCorrect() throws Exception {

        byte[] payload = Base64.decodeBase64("R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs=");

        mockMvc.perform(post("/api/v1/tiny.gif").content(payload))
            .andExpect(status().isOk())
            .andExpect(content().json("{identifier: 'some-identifier'}"));
    }
}
