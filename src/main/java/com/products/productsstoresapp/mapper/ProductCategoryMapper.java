package com.products.productsstoresapp.mapper;

import com.products.productsstoresapp.model.ProductCategory;
import com.products.productsstoresapp.transfer.resource.ProductCategoryResource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {
    ProductCategoryMapper INSTANCE = Mappers.getMapper(ProductCategoryMapper.class);

    ProductCategoryResource toResource(ProductCategory productCategory);

    ProductCategory toDomain(ProductCategoryResource productCategoryResource);

}
