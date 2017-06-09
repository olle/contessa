package com.studiomediatech.contessa.ui.rest;

import com.studiomediatech.contessa.ui.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class Builder {

    public Map<String, Object> buildUploadResult(String identifier) {

        Map<String, Object> result = new HashMap<>();

        result.put("identifier", identifier);

        return result;
    }
}
