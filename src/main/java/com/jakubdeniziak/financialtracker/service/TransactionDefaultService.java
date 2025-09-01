package com.jakubdeniziak.financialtracker.service;

import com.jakubdeniziak.financialtracker.domain.Transaction;
import com.jakubdeniziak.financialtracker.entity.TransactionEntity;
import com.jakubdeniziak.financialtracker.mapper.TransactionMapper;
import com.jakubdeniziak.financialtracker.repository.AssetJpaRepository;
import com.jakubdeniziak.financialtracker.repository.TransactionJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionDefaultService implements TransactionService {

    private final TransactionJpaRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AssetJpaRepository assetRepository;

    @Override
    public void create(Transaction transaction) {
        transactionRepository.save(transactionMapper.map(transaction));
    }

    @Override
    public Transaction read(Long id) {
        return transactionMapper.map(transactionRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<Transaction> readAll() {
        return transactionMapper.map(transactionRepository.findAll());
    }

    @Override
    public void update(Long id, Transaction transaction) {
        TransactionEntity existing = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + id));
        existing.setType(transaction.getType());
        existing.setQuantity(transaction.getQuantity());
        existing.setPricePerUnit(transaction.getPricePerUnit());
        existing.setCurrency(transaction.getCurrency());
        existing.setExecutedAt(transaction.getExecutedAt());
        existing.setNotes(transaction.getNotes());
        existing.setAsset(assetRepository.findById(transaction.getAsset().getId())
                .orElseThrow(() -> new EntityNotFoundException("Asset not found with ID: " + transaction.getAsset().getId())));
        transactionRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

}
