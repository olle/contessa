package com.studiomediatech.contessa.ui;

public final class TestHandler implements Handler {

    @Override
    public Upload handleContentQuery(Query query) {

        return null;
    }


    @Override
    public String handleUploadData(Upload data) {

        return String.format("test-%s", data.filename);
    }
}
