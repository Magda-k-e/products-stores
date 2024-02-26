package com.products.productsstoresapp.mapper;

import com.products.productsstoresapp.model.OrderItem;
import com.products.productsstoresapp.model.Product;
import com.products.productsstoresapp.transfer.resource.OrderItemResource;
import com.products.productsstoresapp.transfer.resource.ProductResource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    OrderItemResource toResource(OrderItem orderItem);

    OrderItem toDomain(OrderItemResource orderItemResource);
}

