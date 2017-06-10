package com.studiomediatech.contessa.storage.nosql;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.storage.Storage;

import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class NoSqlStorageImpl implements Storage, Loggable {

    @Override
    public void store(Entry entry) {

        // TODO Auto-generated method stub
    }


    @Override
    public Optional<Entry> retrieve(String id, String suffix) {

        // TODO Auto-generated method stub
        return null;
    }
}
