package com.studiomediatech.contessa.ui.amqp;

import org.springframework.amqp.core.Message;


public class Converter {

    public UploadEvent convertToUploadEvent(Message message) {

        UploadEvent event = new UploadEvent();

        event.payload = message.getBody();
        event.filename = (String) message.getMessageProperties().getHeaders().get("filename");
        event.replyTo = message.getMessageProperties().getReplyTo();

        return event;
    }
}
