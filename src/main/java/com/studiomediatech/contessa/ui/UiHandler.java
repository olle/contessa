package com.studiomediatech.contessa.ui;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.ui.amqp.UploadEvent;

import java.util.Map;
import java.util.Optional;


/**
 * Defines the capabilities that are available to any UI, for handling of a media-upload or request.
 */
public interface UiHandler extends Loggable {

    Entry handle(UploadEvent event);


    Entry handle(UploadRequest request);


    Optional<Entry> handle(ContentRequest request);


    Map<String, Object> handle(InfoRequest request);
}
