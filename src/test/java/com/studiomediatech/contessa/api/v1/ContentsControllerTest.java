package com.studiomediatech.contessa.api.v1;

import com.studiomediatech.contessa.contents.ContentsService;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.util.ResourceUtils;

import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
public class ContentsControllerTest {

    @Mock
    private ContentsService contentsService;

    @Test
    public void ensureHandlesContentEntryPost() throws Exception {

        byte[] image = Files.readAllBytes(ResourceUtils.getFile("classpath:tinytrans.gif").toPath());

        MockMvcBuilders.standaloneSetup(new ContentsController(contentsService))
            .build()
            .perform(post("/api/v1/contents/foobar.gif").content(image))
            .andExpect(status().isCreated());
    }
}
