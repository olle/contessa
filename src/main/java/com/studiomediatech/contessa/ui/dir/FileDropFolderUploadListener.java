package com.studiomediatech.contessa.ui.dir;

import com.studiomediatech.contessa.app.autoconfigure.ContessaProperties;
import com.studiomediatech.contessa.logging.Loggable;

import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Component;

import java.io.IOException;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Custom user interface component, allows for direct file upload using a local drop- and pickup-folder, where files
 * are consumed by a scheduled task.
 */
@Component
public class FileDropFolderUploadListener implements Loggable {

    private final ContessaProperties props;
    private final AtomicReference<WatchService> watcher = new AtomicReference<>(null);

    public FileDropFolderUploadListener(ContessaProperties props) {

        this.props = props;
    }

    @Scheduled(fixedDelay = 3000)
    public void checkForFileUploaded() throws IOException {

        logger().debug("Polling for new files...");

        if (watcher.compareAndSet(null, FileSystems.getDefault().newWatchService())) {
            Path dir;
            dir = Paths.get(props.getBaseDir(), "dropbox");
            dir.toFile().mkdirs();
            dir.register(watcher.get(), StandardWatchEventKinds.ENTRY_CREATE);
        }

        WatchKey take;

        try {
            take = watcher.get().poll(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return;
        }

        if (Objects.isNull(take)) {
            return;
        }

        for (WatchEvent<?> event : take.pollEvents()) {
            if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
                continue;
            }

            logger().info("I found a new file: {}", event.context());
        }

        if (!take.reset()) {
            watcher.getAndSet(null).close();
        }
    }
}
