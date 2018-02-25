package com.studiomediatech.contessa.ui.dropbox;

import java.io.IOException;


public interface DirSink {

    void writeContentDroppedResponse(String filename, String identifier, String suffix) throws IOException;
}
