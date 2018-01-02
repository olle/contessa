package com.studiomediatech.contessa.ui;

/**
 * Defines the capabilities that are available to any UI, for handling of a media-upload or request.
 */
public interface Handler {

    Upload handleContentQuery(Query query);


    String handleUploadData(Upload data);
}
