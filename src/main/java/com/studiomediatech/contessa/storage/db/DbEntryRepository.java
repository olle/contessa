package com.studiomediatech.contessa.storage.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DbEntryRepository extends CrudRepository<DbEntry, Long> {

    Optional<DbEntry> findOneByIdentifierAndSuffix(String identifier, String suffix);
}
