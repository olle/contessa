package com.studiomediatech.contessa.store.redis;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface RedisEntryRepository extends CrudRepository<RedisEntry, String> {

    // OK
}
