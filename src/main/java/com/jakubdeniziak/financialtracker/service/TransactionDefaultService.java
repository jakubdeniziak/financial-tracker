package com.jakubdeniziak.financialtracker.service;

import com.jakubdeniziak.financialtracker.domain.Transaction;
import com.jakubdeniziak.financialtracker.mapper.TransactionMapper;
import com.jakubdeniziak.financialtracker.repository.TransactionJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDefaultService implements TransactionService {

    private TransactionJpaRepository repository;
    private TransactionMapper mapper;

    @Override
    public void save(Transaction transaction) {
        repository.save(mapper.map(transaction));
    }

    @Override
    public Transaction read(long id) {
        return mapper.map(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<Transaction> readAll() {
        return mapper.map(repository.findAll());
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

}
