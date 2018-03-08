package com.studiomediatech.contessa.ui.amqp;

import org.springframework.amqp.core.Message;


/**
 * Data structure, encapsulating the information of an upload event.
 */
public class UploadEvent {

    public final byte[] payload;
    public final String filename;
    public final String replyTo;

    private UploadEvent(byte[] payload, String filename, String replyTo) {

        this.payload = payload;
        this.filename = filename;
        this.replyTo = replyTo;
    }

    public static UploadEvent valueOf(Message message) {

        byte[] payload = message.getBody();
        String filename = (String) message.getMessageProperties().getHeaders().get("filename");
        String replyTo = message.getMessageProperties().getReplyTo();

        return new UploadEvent(payload, filename, replyTo);
    }
}
