package com.ing.wallet.domain.repository;

import com.ing.wallet.domain.model.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> find(Long id);
    Customer findOrFail(Long id);
    Customer save(Customer customer);
}