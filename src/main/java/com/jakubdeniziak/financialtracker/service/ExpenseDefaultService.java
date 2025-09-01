package com.jakubdeniziak.financialtracker.service;

import com.jakubdeniziak.financialtracker.domain.Expense;
import com.jakubdeniziak.financialtracker.entity.ExpenseEntity;
import com.jakubdeniziak.financialtracker.mapper.ExpenseMapper;
import com.jakubdeniziak.financialtracker.repository.ExpenseJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExpenseDefaultService implements ExpenseService {

    private final ExpenseJpaRepository repository;
    private final ExpenseMapper mapper;

    @Override
    public void create(Expense expense) {
        repository.save(mapper.map(expense));
    }

    @Override
    public Expense read(Long id) {
        return mapper.map(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<Expense> readAll() {
        return mapper.map(repository.findAll());
    }

    @Override
    public void update(Long id, Expense expense) {
        ExpenseEntity existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with ID: " + id));
        existing.setYearMonth(expense.getYearMonth());
        existing.setAmount(expense.getAmount());
        existing.setCurrency(expense.getCurrency());
        repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Map<YearMonth, BigDecimal> calculateRollingAverage(int numberOfMonths) {
        List<Expense> expenses = readAll().stream()
                .sorted(Comparator.comparing(Expense::getYearMonth))
                .toList();
        Map<YearMonth, BigDecimal> rollingAverage = new LinkedHashMap<>();
        Deque<BigDecimal> window = new ArrayDeque<>();
        BigDecimal sum = BigDecimal.ZERO;
        for (Expense expense : expenses) {
            window.addLast(expense.getAmount());
            sum = sum.add(expense.getAmount());
            if (window.size() > numberOfMonths) {
                sum = sum.subtract(window.removeFirst());
            }
            if (window.size() == numberOfMonths) {
                BigDecimal avg = sum.divide(BigDecimal.valueOf(numberOfMonths), RoundingMode.HALF_UP);
                rollingAverage.put(expense.getYearMonth(), avg);
            }
        }
        return rollingAverage;
    }

}
