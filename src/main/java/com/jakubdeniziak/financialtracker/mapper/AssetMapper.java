package com.jakubdeniziak.financialtracker.mapper;

import com.jakubdeniziak.financialtracker.domain.Asset;
import com.jakubdeniziak.financialtracker.entity.AssetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssetMapper {

    @Mapping(target = "id", ignore = true)
    AssetEntity map(Asset asset);

    Asset map(AssetEntity assetEntity);

    List<Asset> map(List<AssetEntity> assetEntities);

}
