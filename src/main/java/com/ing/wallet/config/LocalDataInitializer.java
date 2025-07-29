package com.ing.wallet.config;

import com.ing.wallet.domain.model.Customer;
import com.ing.wallet.domain.repository.CustomerRepository;
import com.ing.wallet.domain.vo.Tckn;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@Configuration
@Profile("local")
public class LocalDataInitializer {

    @Bean
    public CommandLineRunner loadInitialCustomers(CustomerRepository customerRepository) {
        Customer customer1 = new Customer(null, "Ali", "Veli", new Tckn("12345678901"), LocalDate.now());
        Customer customer2 = new Customer(null, "Ayşe", "Yılmaz", new Tckn("23456789012"), LocalDate.now());
        Customer customer3 = new Customer(null, "Mehmet", "Kaya", new Tckn("34567890123"), LocalDate.now());

        return args -> {
            customerRepository.save(customer1);
            customerRepository.save(customer2);
            customerRepository.save(customer3);
        };
    }
}