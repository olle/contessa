package com.studiomediatech.contessa.ui;

import com.studiomediatech.contessa.contents.ContentsService;
import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.ui.amqp.UploadEvent;
import com.studiomediatech.contessa.ui.rest.ContentRequestCommand;

import java.util.Optional;


public class UiHandlerImpl implements UiHandler {

    private final ContentsService contentsService;

    public UiHandlerImpl(ContentsService contentsService) {

        this.contentsService = contentsService;
    }

    @Override
    public String handleUploadCommand(UploadCommand data) {

        return log_returns(contentsService.addMediaContent(data.filename, data.payload).getId());
    }


    @Override
    public String handleUploadEvent(UploadEvent event) {

        return log_returns(contentsService.addMediaContent(event.filename, event.payload).getId());
    }


    @Override
    public Entry handle(UploadCommand command) {

        return log_returns(contentsService.addMediaContent(command.filename, command.payload));
    }


    @Override
    public Optional<Entry> handle(ContentRequestCommand command) {

        return log_returns(contentsService.getMediaContentForIdentifier(command.identifier));
    }
}
