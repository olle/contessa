package com.studiomediatech.contessa.storage.sql;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SqlEntryRepository extends JpaRepository<SqlEntry, String> {

    Optional<SqlEntry> findOneByIdentifier(String identifier);
}
