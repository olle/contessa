package com.studiomediatech.contessa.ui.dropbox;

import java.util.function.Consumer;


public interface DirFilter {

    boolean reject(String filename, Consumer<String> cause);
}
