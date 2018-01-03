package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.validation.ValidationService;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;

import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class ValidatorImplTest {

    @Mock
    ValidationService validationService;

    @InjectMocks
    ValidatorImpl sut;

    @Test
    public void ensureCallsValidationForAmqpMessage() throws Exception {

        Message message = MessageBuilder.withBody("nop".getBytes()).build();
        sut.validateUpload(message);

        verify(validationService).validReplyTo(message);
        verify(validationService).validFilename(message);
    }
}
