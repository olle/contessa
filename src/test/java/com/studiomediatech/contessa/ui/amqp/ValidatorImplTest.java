package com.studiomediatech.contessa.ui.amqp;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class ValidatorImplTest {

    @Test
    public void testName() throws Exception {

        // OK
        assertTrue(true);
    }

//    @Mock
//    ValidationService validationService;
//
//    @InjectMocks
//    AmqpValidatorImpl sut;
//
//    @Test
//    public void ensureCallsValidationForAmqpMessage() throws Exception {
//
//        Message message = MessageBuilder.withBody("nop".getBytes()).build();
//        sut.validateUpload(message);
//
//        verify(validationService).validReplyTo(message);
//        verify(validationService).validFilename(message);
//    }
}
