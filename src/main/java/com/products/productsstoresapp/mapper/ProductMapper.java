package com.products.productsstoresapp.mapper;
import com.products.productsstoresapp.model.Product;
import com.products.productsstoresapp.transfer.resource.ProductResource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResource toResource(Product product);

    Product toDomain(ProductResource productResource);
}
