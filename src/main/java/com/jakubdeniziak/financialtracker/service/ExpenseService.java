package com.jakubdeniziak.financialtracker.service;

import com.jakubdeniziak.financialtracker.domain.Expense;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Map;

public interface ExpenseService extends CrudService<Expense, Long> {

    Map<YearMonth, BigDecimal> calculateRollingAverage(int numberOfMonths);

}
