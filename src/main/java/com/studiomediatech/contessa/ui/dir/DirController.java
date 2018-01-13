package com.studiomediatech.contessa.ui.dir;

import com.studiomediatech.contessa.logging.Loggable;

import org.springframework.stereotype.Component;


/**
 * Custom component that controls and directs the behaviour of the drop-box based user interface.
 */
@Component
public class DirController implements Loggable {

    // OK

    public void handleContentDropped(String filename, byte[] payload) {

        // 0) filter (our own)
        // 1) validate
        // 2) delegate to handling
        // 3) create write response

    }
}
