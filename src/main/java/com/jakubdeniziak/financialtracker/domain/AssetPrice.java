package com.jakubdeniziak.financialtracker.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Value
@Builder
public class AssetPrice {

    long id;
    Asset asset;
    BigDecimal price;
    ZonedDateTime recordedAt;

}
