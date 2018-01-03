package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.logging.Loggable;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;

import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration("amqp")
@Import(RabbitAutoConfiguration.class)
public class Config implements Loggable {

    @Bean
    TopicExchange contessaExchange() {

        return log_created((TopicExchange) ExchangeBuilder.topicExchange("contessa").durable(true).build());
    }


    @Bean
    public Queue contessaContentUploadQueue() {

        return log_created(QueueBuilder.durable("upload").build());
    }


    @Bean
    Binding contessageContentUploadQueueBinding() {

        return log_created(BindingBuilder.bind(contessaContentUploadQueue()).to(contessaExchange()).with("upload"));
    }
}
