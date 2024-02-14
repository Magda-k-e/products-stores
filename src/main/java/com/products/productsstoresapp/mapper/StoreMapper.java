package com.products.productsstoresapp.mapper;

import com.products.productsstoresapp.model.Store;
import com.products.productsstoresapp.transfer.resource.StoreResource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

    StoreResource toResource(Store store);

    Store toDomain(StoreResource storeResource);
}
