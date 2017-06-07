package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.ui.Data;
import com.studiomediatech.contessa.ui.Service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


/**
 * Listener component that handles content upload messages.
 */
public class ContentUploadListener implements Loggable {

    private final Converter converter;
    private final Validator validator;
    private final Service service;
    private final MessageBuilder builder;
    private final Sender sender;

    public ContentUploadListener(Converter converter, Validator validator, Service service, MessageBuilder builder,
        Sender sender) {

        this.converter = converter;
        this.validator = validator;
        this.service = service;
        this.builder = builder;
        this.sender = sender;
    }

    @RabbitListener(queues = "#{@contentUploadQueue}")
    public void handleContentUpload(Message message) {

        Data data = converter.convertUploadMessage(message);
        validator.validateUploadData(data);

        String identifier = service.handleUploadData(data);
        Reply reply = builder.buildUploadReply(identifier, data);
        sender.sendUploadReply(reply);
    }


    @RabbitListener(queues = "#{@contentQueryQueue}")
    public void handleContentQuery(Message message) {

        Query query = converter.convertContentQueryMessage(message);
        validator.validateContentQuery(query);

        Data data = service.handleContentQuery(query);
        Reply reply = builder.buildContentQueryReply(data, query);
        sender.sendContentQueryReply(reply);
    }
}
