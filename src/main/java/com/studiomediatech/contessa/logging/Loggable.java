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


    default <T> T log_returns(T obj) {

        logger().info("Returns {}", obj);

        return obj;
    }


    default void log_received(Message message) {

        logger().info("|--> Received {}", message);
    }


    default void log_sent(Address address, Message message) {

        logger().info("|<-- Sent {} to {}", message, address);
    }
}
