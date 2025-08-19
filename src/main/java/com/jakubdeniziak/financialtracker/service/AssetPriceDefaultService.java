package com.jakubdeniziak.financialtracker.service;

import com.jakubdeniziak.financialtracker.domain.AssetPrice;
import com.jakubdeniziak.financialtracker.mapper.AssetPriceMapper;
import com.jakubdeniziak.financialtracker.repository.AssetPriceJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetPriceDefaultService implements AssetPriceService {

    private final AssetPriceJpaRepository repository;
    private final AssetPriceMapper mapper;

    @Override
    public void save(AssetPrice assetPrice) {
        repository.save(mapper.map(assetPrice));
    }

    @Override
    public AssetPrice read(long id) {
        return mapper.map(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<AssetPrice> readAll() {
        return mapper.map(repository.findAll());
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

}
