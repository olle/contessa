package com.studiomediatech.contessa.ui;

import com.studiomediatech.contessa.contents.ContentsService;
import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.ui.amqp.UploadEvent;

import org.springframework.util.StringUtils;

import java.util.Optional;


public class UiHandlerImpl implements UiHandler {

    private final ContentsService contentsService;

    public UiHandlerImpl(ContentsService contentsService) {

        this.contentsService = contentsService;
    }

    @Override
    public Entry handle(UploadEvent event) {

        return log_returns(contentsService.addMediaContent(event.filename, event.payload));
    }


    @Override
    public Entry handle(UploadRequest command) {

        return log_returns(contentsService.addMediaContent(command.filename, command.payload));
    }


    @Override
    public Optional<Entry> handle(ContentRequest command) {

        Optional<Entry> entry = Optional.empty();

        if (StringUtils.hasText(command.name)) {
            entry = contentsService.getMediaContentForName(command.name);
        }

        if (StringUtils.hasText(command.identifier)) {
            entry = contentsService.getMediaContentForIdentifier(command.identifier);
        }

        return log_returns(entry);
    }
}
