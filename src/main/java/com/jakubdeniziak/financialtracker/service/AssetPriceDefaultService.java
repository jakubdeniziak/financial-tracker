package com.jakubdeniziak.financialtracker.service;

import com.jakubdeniziak.financialtracker.domain.AssetPrice;
import com.jakubdeniziak.financialtracker.entity.AssetPriceEntity;
import com.jakubdeniziak.financialtracker.mapper.AssetMapper;
import com.jakubdeniziak.financialtracker.mapper.AssetPriceMapper;
import com.jakubdeniziak.financialtracker.repository.AssetPriceJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetPriceDefaultService implements AssetPriceService {

    private final AssetPriceJpaRepository repository;
    private final AssetPriceMapper assetPriceMapper;
    private final AssetMapper assetMapper;

    @Override
    public void create(AssetPrice assetPrice) {
        repository.save(assetPriceMapper.map(assetPrice));
    }

    @Override
    public AssetPrice read(Long id) {
        return assetPriceMapper.map(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<AssetPrice> readAll() {
        return assetPriceMapper.map(repository.findAll());
    }

    @Override
    public Optional<BigDecimal> readLatestPriceForAsset(long assetId) {
        return repository.findFirstByAssetIdOrderByRecordedAtDesc(assetId)
                .map(AssetPriceEntity::getPrice);
    }

    @Override
    public List<AssetPrice> readAllForAsset(long assetId) {
        return assetPriceMapper.map(repository.findAllByAsset_Id(assetId));
    }

    @Override
    public void update(Long id, AssetPrice assetPrice) {
        AssetPriceEntity existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AssetPrice not found with ID: " + id));
        existing.setAsset(assetMapper.map(assetPrice.getAsset()));
        existing.setPrice(assetPrice.getPrice());
        existing.setRecordedAt(assetPrice.getRecordedAt());
        repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
