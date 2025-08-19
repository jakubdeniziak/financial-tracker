package com.jakubdeniziak.financialtracker.domain;

import com.jakubdeniziak.financialtracker.entity.AssetCategory;
import com.jakubdeniziak.financialtracker.entity.AssetType;
import lombok.Value;

@Value
public class Asset {

    long id;
    String name;
    String symbol;
    AssetCategory category;
    AssetType type;
    String currency;

}
