package com.studiomediatech.contessa.api.v1;

import com.studiomediatech.contessa.contents.ContentsService;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.file.Files;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

import static org.mockito.Matchers.anyString;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
public class ContentsControllerTest {

    @Mock
    ContentsService contentsService;

    byte[] imageFixture;
    MockMvc mockMvc;

    @Before
    public void setup() throws FileNotFoundException, IOException {

        imageFixture = Files.readAllBytes(ResourceUtils.getFile("classpath:tinytrans.gif").toPath());
        mockMvc = MockMvcBuilders.standaloneSetup(new ContentsController(contentsService)).build();
    }


    @Test
    public void ensureHandlesContentEntryPost() throws Exception {

        mockMvc.perform(post("/api/v1/contents/foobar.gif").content(imageFixture)).andExpect(status().isCreated());
    }


    @Test
    public void ensureReturnsContentsForFoundReference() throws Exception {

        Map<String, Object> obj = new HashMap<>();
        obj.put("image", imageFixture);

        when(contentsService.getMediaContent(anyString())).thenReturn(obj);

        mockMvc.perform(get("/api/v1/contents/code.gif"))
            .andExpect(status().isOk())
            .andExpect(content().bytes(imageFixture));

        verify(contentsService).getMediaContent("code.gif");
    }
}
