package com.ing.wallet.infrastructure.h2.impl;

import com.ing.wallet.domain.exception.CustomerNotFoundException;
import com.ing.wallet.domain.model.Customer;
import com.ing.wallet.domain.repository.CustomerRepository;
import com.ing.wallet.infrastructure.h2.JpaCustomerRepository;
import com.ing.wallet.infrastructure.h2.entity.CustomerEntity;
import com.ing.wallet.infrastructure.h2.mapper.CustomerMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JpaCustomerRepository jpaCustomerRepository;
    private final CustomerMapper customerMapper;

    public CustomerRepositoryImpl(JpaCustomerRepository jpaCustomerRepository, CustomerMapper customerMapper) {
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Optional<Customer> find(Long id) {
        return jpaCustomerRepository.findById(id)
                .map(customerMapper::toDomain);
    }

    @Override
    public Customer findOrFail(Long id) {
        return this.find(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + id));
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = customerMapper.toEntity(customer);
        CustomerEntity savedEntity = jpaCustomerRepository.save(entity);

        return customerMapper.toDomain(savedEntity);
    }
}