package com.studiomediatech.contessa.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.Message;


/**
 * Logging trait.
 */
public interface Loggable {


    default Logger logger() {

        return LoggerFactory.getLogger(this.getClass());
    }


    default <T> T log_created(T obj) {

        logger().info("// Created {}", obj);

        return obj;
    }


    default String log_returns(String returns) {

        logger().info("Returns {}", returns);

        return returns;
    }


    default void log_received(Message message) {

        logger().info("|--> Received {}", message);
    }


    default void log_sent(Address address, Message message) {

        logger().info("|<-- Sent {} to {}", message, address);
    }
}
