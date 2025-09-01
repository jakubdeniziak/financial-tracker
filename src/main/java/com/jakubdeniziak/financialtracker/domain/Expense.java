package com.jakubdeniziak.financialtracker.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.YearMonth;

@Value
@Builder
public class Expense {

    Long id;
    YearMonth yearMonth;
    BigDecimal amount;
    String currency;

}
