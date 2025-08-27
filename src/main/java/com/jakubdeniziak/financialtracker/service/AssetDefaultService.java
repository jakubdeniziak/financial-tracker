package com.jakubdeniziak.financialtracker.service;

import com.jakubdeniziak.financialtracker.domain.Asset;
import com.jakubdeniziak.financialtracker.entity.AssetEntity;
import com.jakubdeniziak.financialtracker.mapper.AssetMapper;
import com.jakubdeniziak.financialtracker.repository.AssetJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetDefaultService implements AssetService {

    private final AssetJpaRepository repository;
    private final AssetMapper mapper;

    @Override
    public void save(Asset asset) {
        repository.save(mapper.map(asset));
    }

    @Override
    public Asset read(long id) {
        return mapper.map(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<Asset> readAll() {
        return mapper.map(repository.findAll());
    }

    @Override
    public void update(Long id, Asset asset) {
        AssetEntity existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Asset not found with ID: " + id));
        existing.setName(asset.getName());
        existing.setSymbol(asset.getSymbol());
        existing.setCategory(asset.getCategory());
        existing.setType(asset.getType());
        existing.setCurrency(asset.getCurrency());
        repository.save(existing);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

}
