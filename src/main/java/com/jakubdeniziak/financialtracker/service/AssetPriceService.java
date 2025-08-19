package com.jakubdeniziak.financialtracker.service;

import com.jakubdeniziak.financialtracker.domain.AssetPrice;

import java.util.List;

public interface AssetPriceService {

    void save(AssetPrice assetPrice);
    AssetPrice read(long id);
    List<AssetPrice> readAll();
    void delete(long id);

}
