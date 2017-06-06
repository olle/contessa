package com.studiomediatech.contessa.storage.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;


@NoRepositoryBean
public interface DbEntryRepository extends CrudRepository<DbEntry, Long> {

    Optional<DbEntry> findOneByIdentifierAndSuffix(String identifier, String suffix);
}
