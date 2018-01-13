package com.studiomediatech.contessa.ui.dir;

import com.studiomediatech.contessa.logging.Loggable;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;


@Component
public class DirFilterImpl implements DirFilter, Loggable {

    @Override
    public boolean reject(String filename, Consumer<String> cause) {

        logger().debug("Filter checking if we reject {}", filename);

        if (filename.matches("^\\[.+\\].*")) {
            cause.accept("response files, starting with '[...]...', are ignored.");

            return true;
        }

        return false;
    }
}
