package com.products.productsstoresapp.mapper;

import com.products.productsstoresapp.model.Account;
import com.products.productsstoresapp.model.OrderItem;
import com.products.productsstoresapp.transfer.resource.AccountResource;
import com.products.productsstoresapp.transfer.resource.OrderItemResource;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountResource toResource(Account account);

    Account toDomain(AccountResource accountResource);
}
