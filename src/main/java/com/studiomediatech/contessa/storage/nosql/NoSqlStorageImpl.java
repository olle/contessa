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

        logger().warn("Not storing anything!");
    }


    @Override
    public Optional<Entry> retrieve(String identifier) {

        logger().warn("Not retrieving anything!");

        return Optional.empty();
    }
}
