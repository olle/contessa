package com.studiomediatech.contessa.ui;

import com.studiomediatech.contessa.contents.ContentsService;
import com.studiomediatech.contessa.ui.rest.UploadCommand;


public class UiHandlerImpl implements UiHandler {

    private final ContentsService contentsService;

    public UiHandlerImpl(ContentsService contentsService) {

        this.contentsService = contentsService;
    }

    @Override
    public String handleUploadCommand(UploadCommand data) {

        return log_returns(contentsService.addMediaContent(data.filename, data.payload));
    }
}
