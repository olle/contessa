package com.studiomediatech.contessa.ui;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.ui.amqp.UploadEvent;
import com.studiomediatech.contessa.ui.rest.ContentRequestCommand;
import com.studiomediatech.contessa.ui.rest.UploadCommand;


/**
 * Defines the capabilities that are available to any UI, for handling of a media-upload or request.
 */
public interface UiHandler extends Loggable {


    default String handleUploadCommand(UploadCommand command) {

        logger().warn("Not handling command {}", command);

        return null;
    }


    default String handleUploadEvent(UploadEvent event) {

        logger().warn("Not handling event {}", event);

        return null;
    }


    default Entry handleContentRequestCommand(ContentRequestCommand command) {

        logger().warn("Not handling command {}", command);

        return null;
    }
}
