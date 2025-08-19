package com.jakubdeniziak.financialtracker.mapper;

import com.jakubdeniziak.financialtracker.domain.Transaction;
import com.jakubdeniziak.financialtracker.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    TransactionEntity map(Transaction transaction);

    Transaction map(TransactionEntity transactionEntity);

    List<Transaction> map(List<TransactionEntity> transactionEntities);

}
