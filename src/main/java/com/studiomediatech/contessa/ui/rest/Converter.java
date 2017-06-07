package com.studiomediatech.contessa.ui.rest;

import com.studiomediatech.contessa.ui.Data;


public class Converter {

    public Data convertToUploadData(String filename, byte[] payload) {

        Data data = new Data();

        data.setFilename(filename);
        data.setPayload(payload);

        return data;
    }
}
