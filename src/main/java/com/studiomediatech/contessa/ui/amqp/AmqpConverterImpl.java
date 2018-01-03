package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.logging.Loggable;

import org.springframework.amqp.core.Message;

import org.springframework.stereotype.Component;


@Component
class AmqpConverterImpl implements AmqpConverter {

    @Override
    public UploadEvent convertToUploadEvent(Message message) {

        UploadEvent event = new UploadEvent();

        event.payload = message.getBody();
        event.filename = (String) message.getMessageProperties().getHeaders().get("filename");
        event.replyTo = message.getMessageProperties().getReplyTo();

        return event;
    }
}

interface AmqpConverter extends Loggable {


    default UploadEvent convertToUploadEvent(Message message) {

        logger().warn("Not converting anything, from {}", message);

        return null;
    }


    default Message convertToUploadResponse(UploadEvent event, String identifier) {

        logger().warn("Not converting anything, from {} and {}", event, identifier);

        return null;
    }
}
