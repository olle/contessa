package com.studiomediatech.contessa.store.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MongoDbEntryRepository extends MongoRepository<MongoDbEntry, String> {

    Optional<MongoDbEntry> findOneByIdentifier(String identifier);
}
