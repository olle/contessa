package com.studiomediatech;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringRunner;

import com.studiomediatech.content.store.ContentServiceApplication;


@RunWith(SpringRunner.class)
public class ContentServiceApplicationTest {

    @Test
    public void contextLoads() {

        ContentServiceApplication.main(new String[] {});
    }
}
