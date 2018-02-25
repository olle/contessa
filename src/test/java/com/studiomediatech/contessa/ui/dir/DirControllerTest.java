package com.studiomediatech.contessa.ui.dir;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.ui.UiHandler;
import com.studiomediatech.contessa.ui.UploadRequest;
import com.studiomediatech.contessa.ui.dropbox.DirController;
import com.studiomediatech.contessa.ui.dropbox.DirFilter;
import com.studiomediatech.contessa.ui.dropbox.DirSink;
import com.studiomediatech.contessa.ui.dropbox.DirValidator;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class DirControllerTest {

    @Mock
    DirFilter filter;
    @Mock
    DirValidator validator;
    @Mock
    UiHandler handler;
    @Mock
    DirSink sink;

    @InjectMocks
    DirController sut;

    @Test
    public void ensureValidatesDelegatesAndResponds() throws IOException {

        when(filter.reject(anyString(), any())).thenReturn(false);

        Entry entry = new Entry();
        entry.setId("entry-id");
        entry.setSuffix("entry-suffix");
        when(handler.handle(any(UploadRequest.class))).thenReturn(entry);

        byte[] payload = "payload".getBytes();
        String filename = "some-filename.jpg";

        sut.handleContentDropped(filename, payload);

        verify(filter).reject(eq(filename), any());
        verify(validator).validateDroppedContent(filename, payload);

        ArgumentCaptor<UploadRequest> captor = ArgumentCaptor.forClass(UploadRequest.class);
        verify(handler).handle(captor.capture());

        assertEquals("Wrong filename", filename, captor.getValue().filename);
        assertEquals("Wrong payload", payload, captor.getValue().payload);

        verify(sink).writeContentDroppedResponse(filename, "entry-id", "entry-suffix");
    }
}
