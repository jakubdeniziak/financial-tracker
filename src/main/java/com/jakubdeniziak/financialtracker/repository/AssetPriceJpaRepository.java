package com.jakubdeniziak.financialtracker.repository;

import com.jakubdeniziak.financialtracker.entity.AssetPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetPriceJpaRepository extends JpaRepository<AssetPriceEntity, Long> {

    List<AssetPriceEntity> findAllByAsset_Id(long assetId);
    Optional<AssetPriceEntity> findFirstByAssetIdOrderByRecordedAtDesc(long assetId);

}
