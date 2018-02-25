package com.studiomediatech.contessa.ui.dir;

import org.junit.Test;

import com.studiomediatech.contessa.ui.dropbox.DirFilterImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class DirFilterImplTest {

    @Test
    public void ensureRejectsResultsMarkerFile() {

        assertTrue(new DirFilterImpl().reject("original.gif___ede542cb-f8ec1d4.gif", System.out::println));
    }


    @Test
    public void ensureAcceptsContentFile() throws Exception {

        assertFalse(new DirFilterImpl().reject("somefile.gif", System.out::println));
    }
}
