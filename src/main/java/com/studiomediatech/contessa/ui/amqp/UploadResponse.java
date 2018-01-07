package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.domain.Entry;

import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.Message;


/**
 * Data structure, containing the AMQP response information, for an upload event.
 */
public class UploadResponse {

    public final Address address;
    public final Message message;

    private UploadResponse(String replyTo) {

        // TODO: Finish this!
        this.address = new Address(replyTo);
        this.message = null;
    }

    public static UploadResponse valueOf(UploadEvent event, Entry entry) {

        return new UploadResponse(event.replyTo);
    }
}
