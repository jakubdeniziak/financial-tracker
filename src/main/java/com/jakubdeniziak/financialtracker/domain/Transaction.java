package com.jakubdeniziak.financialtracker.domain;

import com.jakubdeniziak.financialtracker.entity.TransactionType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Value
@Builder
public class Transaction {

    long id;
    TransactionType type;
    BigDecimal quantity;
    BigDecimal pricePerUnit;
    ZonedDateTime executedAt;
    String notes;
    Asset asset;

}
