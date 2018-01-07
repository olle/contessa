package com.studiomediatech.contessa.ui;

/**
 * Data structure, encapsulating information in a media content request.
 */
public class ContentRequest {

    public final String identifier;
    public final String name;

    private ContentRequest(String identifier, String name) {

        this.identifier = identifier;
        this.name = name;
    }

    public static ContentRequest forName(String name) {

        return new ContentRequest(null, name);
    }


    public static ContentRequest forIdentifer(String identifier) {

        return new ContentRequest(identifier, null);
    }
}
