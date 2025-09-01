package com.jakubdeniziak.financialtracker.service;

import com.jakubdeniziak.financialtracker.domain.Expense;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface ExpenseService {

    void create(Expense expense);
    Expense read(long id);
    List<Expense> readAll();
    void update(long id, Expense expense);
    void delete(long id);
    Map<YearMonth, BigDecimal> calculateRollingAverage(int numberOfMonths);

}
