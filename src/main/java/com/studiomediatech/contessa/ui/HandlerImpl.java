package com.studiomediatech.contessa.ui;

import com.studiomediatech.contessa.contents.ContentsService;
import com.studiomediatech.contessa.ui.amqp.Query;


public class Service {

    private final ContentsService contentsService;

    public Service(ContentsService contentsService) {

        this.contentsService = contentsService;
    }

    public String handleUploadData(Data data) {

        return null;
    }


    public Data handleContentQuery(Query query) {

        return null;
    }
}
