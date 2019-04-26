package com.studiomediatech.contessa.store.sql;

import com.studiomediatech.contessa.domain.Entry;
import com.studiomediatech.contessa.logging.Loggable;
import com.studiomediatech.contessa.store.Storage;
import com.studiomediatech.contessa.store.ContessaException;

import org.springframework.stereotype.Component;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;


@Component
public class SqlStorageImpl implements Storage, Loggable {

    private final SqlEntryRepository repo;

    public SqlStorageImpl(SqlEntryRepository repo) {

        this.repo = repo;
    }

    @Override
    @Transactional(TxType.REQUIRES_NEW)
    public void store(Entry entry) {

        SqlEntry e = SqlEntry.valueOf(entry);

        SqlEntry stored = repo.save(e);
        logger().debug("Saved {}", stored);
    }


    @Override
    public Optional<Entry> retrieve(String identifier) {

        Optional<SqlEntry> entry = repo.findOneByIdentifier(identifier);
        logger().debug("Retrieved {} for identifier: {}", entry, identifier);

        return entry.map(SqlEntry::asEntry);
    }


    @Override
    public long count() {

        return repo.count();
    }

    @Override
    public boolean exists(Entry entry, String path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Entry entry, String hpat) throws ContessaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
