package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.logging.Loggable;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import org.springframework.scheduling.annotation.Async;


/**
 * Listener component that handles content upload messages.
 */
public class ContentUploadListener implements Loggable {

    private final ValidatorImpl validator;
    private final Converter converter;
    private final ApplicationEventPublisher publisher;

    public ContentUploadListener(ValidatorImpl validator, Converter converter, ApplicationEventPublisher publisher) {

        this.validator = validator;
        this.converter = converter;
        this.publisher = publisher;
    }

    @RabbitListener(queues = "#{@contessaContentUploadQueue}")
    public void handleContentUpload(Message message) {

        log_message(message);
        validator.validateUpload(message);

        UploadEvent event = converter.convertToUploadEvent(message);
        publisher.publishEvent(event);
    }


    @Async
    @EventListener
    public void on(UploadEvent event) {

        // TODO: Handle, and publish results.
    }


    @Async
    @EventListener
    public void on(UploadResponseEvent event) {

        // TODO: Send AMQP response in event
    }

//    @RabbitListener(queues = "#{@contentQueryQueue}")
//    public void handleContentQuery(Message message) {
//
//        Query query = converter.convertContentQueryMessage(message);
//        validator.validateContentQuery(query);
//
//        Upload data = service.handleContentQuery(query);
//        Reply reply = builder.buildContentQueryReply(data, query);
//        sender.sendContentQueryReply(reply);
//    }
}
