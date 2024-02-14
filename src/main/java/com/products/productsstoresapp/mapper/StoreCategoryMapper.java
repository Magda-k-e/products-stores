package com.products.productsstoresapp.mapper;

import com.products.productsstoresapp.model.StoreCategory;
import com.products.productsstoresapp.transfer.resource.StoreCategoryResource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StoreCategoryMapper {
    StoreCategoryMapper INSTANCE = Mappers.getMapper(StoreCategoryMapper.class);

    StoreCategoryResource toResource(StoreCategory storeCategory);

    StoreCategory toDomain(StoreCategoryResource storeCategoryResource);
}
