package com.ing.wallet.infrastructure.h2.mapper;

import com.ing.wallet.domain.model.Customer;
import com.ing.wallet.domain.vo.Tckn;
import com.ing.wallet.infrastructure.h2.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    default Customer toDomain(CustomerEntity entity) {
        return new Customer(
                entity.getId(),
                entity.getName(),
                entity.getSurname(),
                new Tckn(entity.getTckn()),
                entity.getCreatedAt()
        );
    }

    default CustomerEntity toEntity(Customer customer) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.id());
        entity.setName(customer.name());
        entity.setSurname(customer.surname());
        entity.setTckn(customer.tckn().value());
        entity.setCreatedAt(customer.createdAt());
        return entity;
    }
}