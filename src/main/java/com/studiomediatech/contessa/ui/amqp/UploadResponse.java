package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.domain.Entry;

import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;


/**
 * Data structure, containing the AMQP response information, for an upload event.
 */
public class UploadResponse {

    public final Address address;
    public final Message message;

    private UploadResponse(Address address, Message message) {

        this.address = address;
        this.message = message;
    }

    public static UploadResponse valueOf(UploadEvent event, Entry entry) {

        Address address = new Address(event.replyTo);

        Message message = MessageBuilder.withBody(String.format("%s.%s", entry.getId(), entry.getSuffix()).getBytes())
                .setContentEncoding("UTF-8")
                .setHeader("identifier", entry.getId())
                .setHeader("suffix", entry.getSuffix())
                .setHeader("type", entry.getType())
                .build();

        return new UploadResponse(address, message);
    }
}
