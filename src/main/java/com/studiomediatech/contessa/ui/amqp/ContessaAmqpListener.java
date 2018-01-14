package com.studiomediatech.contessa.ui.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.context.ApplicationEventPublisher;

import org.springframework.stereotype.Component;


/**
 * Listener component that handles content upload messages.
 */
@Component
public class ContessaAmqpListener implements AmqpLoggable {

    private final AmqpValidator validator;
    private final ApplicationEventPublisher publisher;

    public ContessaAmqpListener(AmqpValidator validator, ApplicationEventPublisher publisher) {

        this.validator = validator;
        this.publisher = publisher;
    }

    @RabbitListener(queues = "#{@contessaContentUploadQueue}")
    public void handleContentUpload(Message message) {

        log_received(message);
        validator.validateUpload(message);
        publisher.publishEvent(UploadEvent.valueOf(message));
    }
}
