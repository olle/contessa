package com.studiomediatech.contessa.store.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface NoSqlEntryRepository extends MongoRepository<NoSqlEntry, String> {

    Optional<NoSqlEntry> findOneByIdentifier(String identifier);
}
