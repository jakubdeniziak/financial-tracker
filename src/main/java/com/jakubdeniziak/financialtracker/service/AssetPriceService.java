package com.jakubdeniziak.financialtracker.service;

import com.jakubdeniziak.financialtracker.domain.AssetPrice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AssetPriceService extends CrudService<AssetPrice, Long> {

    Optional<BigDecimal> readLatestPriceForAsset(long assetId);
    List<AssetPrice> readAllForAsset(long assetId);

}
