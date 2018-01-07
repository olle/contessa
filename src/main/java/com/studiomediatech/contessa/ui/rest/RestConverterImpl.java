package com.studiomediatech.contessa.ui.rest;

import com.studiomediatech.contessa.ui.UploadCommand;


final class RestConverterImpl implements RestConverter {

    @Override
    public UploadCommand convertToUploadCommand(String filename, byte[] payload) {

        UploadCommand target = new UploadCommand();

        target.filename = filename;
        target.payload = payload;

        return target;
    }


    @Override
    public ContentRequestCommand convertToContentRequestCommand(String identifier) {

        ContentRequestCommand target = new ContentRequestCommand();

        target.identifier = identifier;

        return target;
    }
}

interface RestConverter {

    UploadCommand convertToUploadCommand(String filename, byte[] payload);


    ContentRequestCommand convertToContentRequestCommand(String identifier);
}
