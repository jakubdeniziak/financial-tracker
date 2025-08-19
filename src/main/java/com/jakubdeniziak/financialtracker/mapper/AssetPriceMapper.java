package com.jakubdeniziak.financialtracker.mapper;

import com.jakubdeniziak.financialtracker.domain.AssetPrice;
import com.jakubdeniziak.financialtracker.entity.AssetPriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssetPriceMapper {

    @Mapping(target = "id", ignore = true)
    AssetPriceEntity map(AssetPrice assetPrice);

    AssetPrice map(AssetPriceEntity assetPriceEntity);
    List<AssetPrice> map(List<AssetPriceEntity> assetPriceEntities);

}
