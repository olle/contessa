package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.ui.UiHandler;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import org.springframework.scheduling.annotation.Async;

import org.springframework.stereotype.Component;


/**
 * Listener component that handles content upload messages.
 */
@Component
public class ContessaAmqpListener implements AmqpLoggable {

    private final AmqpValidator validator;
    private final ApplicationEventPublisher publisher;
    private final UiHandler handler;
    private final AmqpTemplate template;

    public ContessaAmqpListener(AmqpValidator validator, ApplicationEventPublisher publisher, UiHandler handler,
        AmqpTemplate template) {

        this.validator = validator;
        this.publisher = publisher;
        this.handler = handler;
        this.template = template;
    }

    @RabbitListener(queues = "#{@contessaContentUploadQueue}")
    public void handleContentUpload(Message message) {

        log_received(message);
        validator.validateUpload(message);

        UploadEvent event = UploadEvent.valueOf(message);
        publisher.publishEvent(event);
    }


    @Async
    @EventListener
    public void on(UploadEvent event) {

        Entry entry = handler.handle(event);
        UploadResponse response = UploadResponse.valueOf(event, entry);

        template.send(response.address.getExchangeName(), response.address.getRoutingKey(), response.message);
        log_sent(response);
    }
}
