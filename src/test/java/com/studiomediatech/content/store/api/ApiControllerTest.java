package com.studiomediatech.content.store.api;

import com.studiomediatech.content.store.web.LinkHelper;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
public class ApiControllerTest {

    @Test
    public void ensureHandlesSlashRequest() throws Exception {

        MockMvcBuilders.standaloneSetup(new ApiController(new LinkHelper()))
            .build()
            .perform(get("/"))
            .andExpect(status().isOk());
    }
}
