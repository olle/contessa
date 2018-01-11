package com.studiomediatech.contessa.ui.dir;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


@Configuration
@EnableScheduling
public class DirConfig {

    @Bean
    @ConditionalOnMissingBean
    public TaskScheduler taskScheduler() {

        return new ThreadPoolTaskScheduler();
    }
}
