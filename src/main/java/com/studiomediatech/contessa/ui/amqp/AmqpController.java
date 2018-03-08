package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.ui.UiHandler;
import com.studiomediatech.contessa.ui.UploadRequest;

import org.springframework.amqp.core.AmqpTemplate;

import org.springframework.context.event.EventListener;

import org.springframework.scheduling.annotation.Async;

import org.springframework.stereotype.Component;


/**
 * Custom component that controls and directs the behaviour of the AMQP based user interface.
 */
@Component
public class AmqpController implements AmqpLoggable {

    private final UiHandler handler;
    private final AmqpTemplate template;

    public AmqpController(UiHandler handler, AmqpTemplate template) {

        this.handler = handler;
        this.template = template;
    }

    @Async
    @EventListener
    public void on(UploadEvent event) {

        UploadRequest request = UploadRequest.valueOf(event.filename, event.payload);
        Entry entry = handler.handle(request);
        UploadResponse response = UploadResponse.valueOf(event, entry);
        template.send(response.address.getExchangeName(), response.address.getRoutingKey(), response.message);
        log_sent(response);
    }
}
