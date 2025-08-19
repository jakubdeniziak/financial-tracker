package com.jakubdeniziak.financialtracker.domain;

import com.jakubdeniziak.financialtracker.entity.TransactionType;
import lombok.Value;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Value
public class Transaction {

    long id;
    TransactionType type;
    BigDecimal quantity;
    BigDecimal pricePerUnit;
    String currency;
    ZonedDateTime executedAt;
    String notes;
    Asset asset;

}
