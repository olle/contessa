package com.studiomediatech.contessa.app.autoconfigure;

import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.ui.Service;
import com.studiomediatech.contessa.ui.rest.Builder;
import com.studiomediatech.contessa.ui.rest.ContentUploadController;
import com.studiomediatech.contessa.ui.rest.Converter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * Configurations for the user interface of Contessa.
 */
public class UiAutoConfiguration implements Loggable {

    @Configuration
    @ConditionalOnProperty("contessa.ui.rest.enabled")
    @ComponentScan(basePackageClasses = ContentUploadController.class)
    public static class RestUiAutoConfiguration implements Loggable {

        @Bean
        @ConditionalOnMissingBean
        public Service service() {

            return new Service();
        }


        @Bean
        @ConditionalOnMissingBean
        public Converter restConverter() {

            return new Converter();
        }


        @Bean
        @ConditionalOnMissingBean
        public Builder restBuilder() {

            return new Builder();
        }
    }

    @Configuration
    @ConditionalOnProperty("contessa.ui.amqp.enabled")
    public static class AmqpUiAutoConfiguration implements Loggable {

        // TODO
    }

    @Configuration
    @ConditionalOnProperty("contessa.ui.file.enabled")
    public static class FileUiAutoConfiguration implements Loggable {

        // TODO
    }
}
