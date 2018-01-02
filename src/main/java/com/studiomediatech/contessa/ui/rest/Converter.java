package com.studiomediatech.contessa.ui.rest;

import com.studiomediatech.contessa.ui.Upload;


public class Converter {

    public Upload convertToUpload(String filename, byte[] payload) {

        Upload data = new Upload();

        data.filename = filename;
        data.payload = payload;

        return data;
    }
}
