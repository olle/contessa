package com.studiomediatech.content.store.api;

import org.junit.Test;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ApiControllerTest {

    @Test
    public void ensureHandlesSlashRequest() throws Exception {

        MockMvcBuilders.standaloneSetup(new ApiController()).build().perform(get("/")).andExpect(status().isOk());
    }
}
