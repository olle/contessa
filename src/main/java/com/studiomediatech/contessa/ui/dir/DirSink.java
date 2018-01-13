package com.studiomediatech.contessa.ui.dir;

import java.io.IOException;


public interface DirSink {

    void writeContentDroppedResponse(String filename, String identifier, String suffix) throws IOException;
}
