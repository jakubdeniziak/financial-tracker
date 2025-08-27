package com.jakubdeniziak.financialtracker.service;

import com.jakubdeniziak.financialtracker.domain.Asset;

import java.util.List;

public interface AssetService {

    void save(Asset asset);
    Asset read(long id);
    List<Asset> readAll();
    void update(Long id, Asset asset);
    void delete(long id);

}
