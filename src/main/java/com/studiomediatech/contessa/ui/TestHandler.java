package com.studiomediatech.contessa.ui;

public class TestHandler implements Handler {

    @Override
    public Data handleContentQuery(Query query) {

        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String handleUploadData(Data data) {

        return String.format("test-%s", data.filename);
    }
}
