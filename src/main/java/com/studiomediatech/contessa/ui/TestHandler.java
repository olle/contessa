package com.studiomediatech.contessa.ui;

public class TestHandler implements Handler {

    @Override
    public Upload handleContentQuery(Query query) {

        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String handleUploadData(Upload data) {

        return String.format("test-%s", data.filename);
    }
}
