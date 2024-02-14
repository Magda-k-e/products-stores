package com.products.productsstoresapp.mapper;

import com.products.productsstoresapp.model.Order;
import com.products.productsstoresapp.transfer.resource.OrderResource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderResource toResource(Order order);

    Order toDomain(OrderResource orderResource);
}
