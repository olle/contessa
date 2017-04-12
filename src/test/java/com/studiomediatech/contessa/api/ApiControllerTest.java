package com.studiomediatech.contessa.api;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApiControllerTest {

    @LocalServerPort
    private String port;

    @Test
    public void ensureHandlesSlashRequest() throws Exception {

        MockMvcBuilders.standaloneSetup(new ApiController())
            .build()
            .perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().json("{}"));
    }
}
