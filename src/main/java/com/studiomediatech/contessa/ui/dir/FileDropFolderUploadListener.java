package com.studiomediatech.contessa.ui.dir;

import com.studiomediatech.contessa.app.autoconfigure.ContessaProperties;
import com.studiomediatech.contessa.logging.Loggable;

import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Component;

import java.io.IOException;

import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
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
    private final DirController controller;

    private final AtomicReference<WatchService> watcher = new AtomicReference<>(null);

    public FileDropFolderUploadListener(ContessaProperties props, DirController controller) {

        this.props = props;
        this.controller = controller;
    }

    @Scheduled(fixedDelay = 3000)
    public void checkForFileUploaded() throws IOException {

        if (watcher.compareAndSet(null, FileSystems.getDefault().newWatchService())) {
            getDropboxPath().register(watcher.get(), StandardWatchEventKinds.ENTRY_CREATE);
        }

        WatchKey take;

        try {
            take = watcher.get().poll(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return;
        } catch (ClosedWatchServiceException ce) {
            logger().warn("Drop-box watching failed " + watcher.getAndSet(null), ce);

            return;
        }

        if (Objects.isNull(take)) {
            return;
        }

        for (WatchEvent<?> event : take.pollEvents()) {
            if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
                continue;
            }

            Path file = (Path) event.context();
            logger().info("New file found in the drop-box: {}", file);

            String filename = file.toString();
            Path path = getDropboxPath().resolve(file);

            byte[] payload = Files.readAllBytes(path);
            controller.handleContentDropped(filename, payload);
        }

        if (!take.reset()) {
            watcher.getAndSet(null).close();
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
