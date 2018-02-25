package com.studiomediatech.contessa.store.cassandra;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface CassandraEntryRepository extends CrudRepository<CassandraEntry, String> {

    // OK
}
