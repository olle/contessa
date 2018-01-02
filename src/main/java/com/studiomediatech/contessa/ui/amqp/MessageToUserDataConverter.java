package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.ui.Upload;

import org.springframework.amqp.core.Message;

import org.springframework.core.convert.converter.Converter;


public class MessageToUserDataConverter implements Converter<Message, Upload> {

    @Override
    public Upload convert(Message source) {

        Upload userData = new Upload();

        userData.filename = (String) source.getMessageProperties().getHeaders().get("filename");
        userData.payload = source.getBody();
        userData.correlation = source.getMessageProperties().getReplyTo();

        return userData;
    }
}
