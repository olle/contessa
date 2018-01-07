package com.studiomediatech.contessa.ui.amqp;

import org.springframework.amqp.core.Message;

interface AmqpValidator {

    void validateUpload(Message message);
}