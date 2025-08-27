package com.jakubdeniziak.financialtracker.domain;

import com.jakubdeniziak.financialtracker.entity.AssetCategory;
import com.jakubdeniziak.financialtracker.entity.AssetType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Asset {

    Long id;
    String name;
    String symbol;
    AssetCategory category;
    AssetType type;
    String currency;

}
