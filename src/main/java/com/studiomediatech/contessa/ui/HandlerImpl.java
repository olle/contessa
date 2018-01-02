package com.studiomediatech.contessa.ui;

import com.studiomediatech.contessa.contents.ContentsService;


public class HandlerImpl implements Handler {

    private final ContentsService contentsService;

    public HandlerImpl(ContentsService contentsService) {

        this.contentsService = contentsService;
    }

    @Override
	public String handleUploadData(Upload data) {

        return null;
    }


    @Override
	public Upload handleContentQuery(Query query) {

        return null;
    }
}
