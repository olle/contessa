package com.studiomediatech.contessa.ui.dir;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.ui.UiHandler;
import com.studiomediatech.contessa.ui.UploadRequest;

import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Custom component that controls and directs the behaviour of the drop-box based user interface.
 */
@Component
public class DirController implements Loggable {

    private final DirFilter filter;
    private final DirValidator validator;
    private final UiHandler handler;
    private final DirSink sink;

    public DirController(DirFilter filter, DirValidator validator, UiHandler handler, DirSink sink) {

        this.filter = filter;
        this.validator = validator;
        this.handler = handler;
        this.sink = sink;
    }

    public void handleContentDropped(String filename, byte[] payload) {

        if (filter.reject(filename, reason -> logger().debug("Rejected file '{}' due to: {}", filename, reason))) {
            return;
        }

        validator.validateDroppedContent(filename, payload);

        Entry entry = handler.handle(UploadRequest.valueOf(filename, payload));

        try {
            sink.writeContentDroppedResponse(filename, entry.getId(), entry.getSuffix());
        } catch (IOException e) {
            logger().warn("Failed to write response marker for entry: " + entry, e);
        }
    }
}
