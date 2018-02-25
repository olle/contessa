package com.studiomediatech.contessa.ui.dropbox;

import com.studiomediatech.contessa.logging.Loggable;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;


@Component
public class DirFilterImpl implements DirFilter, Loggable {

    @Override
    public boolean reject(String filename, Consumer<String> cause) {

        logger().debug("Filter checking if we reject {}", filename);

        if (filename.matches("^.+___.*")) {
            cause.accept("response files, matching '{filename}___{identifier}', are ignored.");

            return true;
        }

        return false;
    }
}
