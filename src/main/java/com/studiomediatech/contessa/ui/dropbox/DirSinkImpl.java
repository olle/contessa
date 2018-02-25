package com.studiomediatech.contessa.ui.dropbox;

import com.studiomediatech.contessa.app.autoconfigure.ContessaProperties;
import com.studiomediatech.contessa.logging.Loggable;

import org.springframework.stereotype.Component;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;


@Component
public class DirSinkImpl implements DirSink, Loggable {

    private final ContessaProperties props;

    public DirSinkImpl(ContessaProperties props) {

        this.props = props;
    }

    @Override
    public void writeContentDroppedResponse(String filename, String identifier, String suffix) throws IOException {

        try {
            String response = String.format("%s___%s.%s", filename, identifier, suffix);
            logger().info("Writing response marker-file: {}", response);
            getDropboxPath().resolve(response).toAbsolutePath().toFile().createNewFile();
        } finally {
            getDropboxPath().resolve(filename).toFile().delete();
        }
    }


    private Path getDropboxPath() {

        Path dir = Paths.get(props.getBaseDir(), "dropbox");

        if (!dir.toFile().exists()) {
            dir.toFile().mkdirs();
        }

        return dir;
    }
}
