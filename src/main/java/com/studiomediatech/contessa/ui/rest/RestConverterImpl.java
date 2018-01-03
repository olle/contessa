package com.studiomediatech.contessa.ui.rest;

final class RestConverterImpl implements RestConverter {

    @Override
    public UploadCommand convertToUploadCommand(String filename, byte[] payload) {

        UploadCommand data = new UploadCommand();

        data.filename = filename;
        data.payload = payload;

        return data;
    }
}

interface RestConverter {


    default UploadCommand convertToUploadCommand(String filename, byte[] payload) {

        return new UploadCommand();
    }
}
