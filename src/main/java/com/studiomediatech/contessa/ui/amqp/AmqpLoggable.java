package com.studiomediatech.contessa.ui.amqp;

import com.studiomediatech.contessa.logging.Loggable;

import org.springframework.amqp.core.Message;


public interface AmqpLoggable extends Loggable {


    default void log_received(Message message) {

        logger().info("|--> Received {}", message);
    }


    default void log_sent(UploadResponse response) {

        logger().info("|<-- Sent {} to {}", response.message, response.address);
    }
}
