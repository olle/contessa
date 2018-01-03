package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.ui.UiHandler;

import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import org.springframework.scheduling.annotation.Async;


/**
 * Listener component that handles content upload messages.
 */
public class ContentUploadListener implements Loggable {

    private final AmqpValidator validator;
    private final AmqpConverter converter;
    private final ApplicationEventPublisher publisher;
    private final UiHandler handler;
    private AmqpTemplate template;

    public ContentUploadListener(AmqpValidator validator, AmqpConverter converter, ApplicationEventPublisher publisher,
        UiHandler handler, AmqpTemplate template) {

        this.validator = validator;
        this.converter = converter;
        this.publisher = publisher;
        this.handler = handler;
        this.template = template;
    }

    @RabbitListener(queues = "#{@contessaContentUploadQueue}")
    public void handleContentUpload(Message message) {

        log_received(message);
        validator.validateUpload(message);

        UploadEvent event = converter.convertToUploadEvent(message);
        publisher.publishEvent(event);
    }


    @Async
    @EventListener
    public void on(UploadEvent event) {

        String identifier = handler.handleUploadEvent(event);
        Message message = converter.convertToUploadResponse(event, identifier);
        Address address = new Address(event.replyTo);
        template.sendAndReceive(address.getExchangeName(), address.getRoutingKey(), message);
        log_sent(address, message);
    }
}
