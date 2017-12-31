package com.studiomediatech.contessa.ui.file;

import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.ui.Data;
import com.studiomediatech.contessa.ui.Handler;

import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;


/**
 * Custom user interface component, allows for direct file upload using a local drop- and pickup-folder, where files
 * are consumed by a scheduled task.
 */
public class FileDropFolderUploadListener implements Loggable {

    private Handler service;
    private Converter converter;
    private Validator validator;
    private Builder builder;

    public FileDropFolderUploadListener(Converter converter, Validator validator, Handler service, Builder builder) {

        this.converter = converter;
        this.validator = validator;
        this.service = service;
        this.builder = builder;
    }

    @Scheduled(fixedDelay = 20000)
    public void checkForFileUploaded() {

        // If new file...
        handleUploadedFile(null);
    }


    private void handleUploadedFile(File file) {

        Data data = converter.convertUploadedFile(file);
        validator.validateUploadData(data);
        removeUploadedFile(file);

        String identifier = service.handleUploadData(data);
        File result = builder.buildResultFile(identifier, data);
        writeResultFile(result);
    }


    private void removeUploadedFile(File file) {

        // TODO Auto-generated method stub
    }


    private void writeResultFile(File result) {

        // TODO Auto-generated method stub
    }
}
