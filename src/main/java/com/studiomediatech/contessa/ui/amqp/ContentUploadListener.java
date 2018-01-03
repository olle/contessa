package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.logging.Loggable;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


/**
 * Listener component that handles content upload messages.
 */
public class ContentUploadListener implements Loggable {

    private final ValidatorImpl validator;

    public ContentUploadListener(ValidatorImpl validator) {

        this.validator = validator;
    }

    @RabbitListener(queues = "#{@contentUploadQueue}")
    public void handleContentUpload(Message message) {

        validator.validateUpload(message);
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
