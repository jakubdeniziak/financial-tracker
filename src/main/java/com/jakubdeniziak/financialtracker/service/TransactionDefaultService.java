package com.jakubdeniziak.financialtracker.service;

import com.jakubdeniziak.financialtracker.domain.Transaction;
import com.jakubdeniziak.financialtracker.entity.TransactionEntity;
import com.jakubdeniziak.financialtracker.mapper.AssetMapper;
import com.jakubdeniziak.financialtracker.mapper.TransactionMapper;
import com.jakubdeniziak.financialtracker.repository.TransactionJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDefaultService implements TransactionService {

    private final TransactionJpaRepository repository;
    private final TransactionMapper transactionMapper;
    private final AssetMapper assetMapper;

    @Override
    public void create(Transaction transaction) {
        repository.save(transactionMapper.map(transaction));
    }

    @Override
    public Transaction read(Long id) {
        return transactionMapper.map(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<Transaction> readAll() {
        return transactionMapper.map(repository.findAll());
    }

    @Override
    public void update(Long id, Transaction transaction) {
        TransactionEntity existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + id));
        existing.setType(transaction.getType());
        existing.setQuantity(transaction.getQuantity());
        existing.setPricePerUnit(transaction.getPricePerUnit());
        existing.setCurrency(transaction.getCurrency());
        existing.setExecutedAt(transaction.getExecutedAt());
        existing.setNotes(transaction.getNotes());
        existing.setAsset(assetMapper.map(transaction.getAsset()));
        repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
