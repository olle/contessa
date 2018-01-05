package com.studiomediatech.contessa.ui.rest;

import com.studiomediatech.contessa.logging.Loggable;
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

interface RestConverter extends Loggable {


    default UploadCommand convertToUploadCommand(String filename, byte[] payload) {

        logger().warn("Not converting {} and {}", filename, payload);

        return null;
    }


    default ContentRequestCommand convertToContentRequestCommand(String identifier) {

        logger().warn("Not converting {}", identifier);

        return null;
    }
}
