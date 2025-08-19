package com.jakubdeniziak.financialtracker.service;

import com.jakubdeniziak.financialtracker.domain.Transaction;

import java.util.List;

public interface TransactionService {

    void save(Transaction transaction);
    Transaction read(long id);
    List<Transaction> readAll();
    void delete(long id);

}
