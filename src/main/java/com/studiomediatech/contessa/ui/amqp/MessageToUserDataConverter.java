package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.ui.Data;

import org.springframework.amqp.core.Message;

import org.springframework.core.convert.converter.Converter;


public class MessageToUserDataConverter implements Converter<Message, Data> {

    @Override
    public Data convert(Message source) {

        Data userData = new Data();

        userData.filename = (String) source.getMessageProperties().getHeaders().get("filename");
        userData.payload = source.getBody();
        userData.correlation = source.getMessageProperties().getReplyTo();

        return userData;
    }
}
