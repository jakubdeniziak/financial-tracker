package com.jakubdeniziak.financialtracker.repository;

import com.jakubdeniziak.financialtracker.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseJpaRepository extends JpaRepository<ExpenseEntity, Long> {
}
