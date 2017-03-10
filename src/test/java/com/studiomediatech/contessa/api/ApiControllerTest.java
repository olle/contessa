package com.studiomediatech.contessa.api;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.studiomediatech.contessa.api.ApiController;
import com.studiomediatech.contessa.http.LinkHelper;

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
