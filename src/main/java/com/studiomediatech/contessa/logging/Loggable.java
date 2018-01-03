package com.studiomediatech.contessa.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


    default void log_message(Message message) {

        logger().info("|--> Received {}", message);
    }
}
