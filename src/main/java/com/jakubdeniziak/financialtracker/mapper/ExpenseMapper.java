package com.jakubdeniziak.financialtracker.mapper;

import com.jakubdeniziak.financialtracker.domain.Expense;
import com.jakubdeniziak.financialtracker.entity.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(target = "id", ignore = true)
    ExpenseEntity map(Expense expense);

    Expense map(ExpenseEntity expenseEntity);

    List<Expense> map(List<ExpenseEntity> expenseEntities);

}
