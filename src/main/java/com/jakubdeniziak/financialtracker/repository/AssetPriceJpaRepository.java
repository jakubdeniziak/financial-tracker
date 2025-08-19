package com.jakubdeniziak.financialtracker.repository;

import com.jakubdeniziak.financialtracker.entity.AssetPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetPriceJpaRepository extends JpaRepository<AssetPriceEntity, Long> {
}
