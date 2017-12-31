package com.studiomediatech.contessa.ui.rest;

import java.util.HashMap;
import java.util.Map;


public class Builder {

    public Map<String, Object> buildUploadResult(String identifier) {

        Map<String, Object> result = new HashMap<>();

        result.put("identifier", identifier);

        return result;
    }
}
