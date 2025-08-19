package com.jakubdeniziak.financialtracker.repository;

import com.jakubdeniziak.financialtracker.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, Long> {
}
