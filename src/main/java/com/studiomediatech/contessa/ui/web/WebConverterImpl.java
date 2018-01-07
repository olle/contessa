package com.studiomediatech.contessa.ui.web;

import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.ui.rest.ContentRequestCommand;


public final class WebConverterImpl implements WebConverter {

    @Override
    public ContentRequestCommand convertToContentRequestCommand(String name) {

        ContentRequestCommand target = new ContentRequestCommand();

        target.name = name;

        return target;
    }
}

interface WebConverter extends Loggable {

    ContentRequestCommand convertToContentRequestCommand(String name);
}
