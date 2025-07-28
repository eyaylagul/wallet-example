package com.ing.wallet.domain.model;

import com.ing.wallet.domain.vo.Tckn;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void withValidFields_shouldCreateCustomer() {
        Tckn tckn = new Tckn("12345678901");

        Customer customer = Customer.builder()
                .id(1L)
                .name("Ali")
                .surname("Veli")
                .tckn(tckn)
                .createdAt(LocalDate.now())
                .build();

        assertEquals("Ali", customer.getName());
        assertEquals("Veli", customer.getSurname());
        assertEquals(tckn.value(), customer.getTckn().value());
        assertNotNull(customer.getCreatedAt());
    }

    @Test
    void whenNameIsNull_shouldThrowException() {
        Tckn tckn = new Tckn("12345678901");

        assertThrows(NullPointerException.class, () -> Customer.builder()
                .surname("Veli")
                .tckn(tckn)
                .createdAt(LocalDate.now())
                .build());
    }

    @Test
    void whenSurnameIsNull_shouldThrowException() {
        Tckn tckn = new Tckn("12345678901");

        assertThrows(NullPointerException.class, () -> Customer.builder()
                .name("Ali")
                .tckn(tckn)
                .createdAt(LocalDate.now())
                .build());
    }

    @Test
    void whenTcknIsNull_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> Customer.builder()
                .name("Ali")
                .surname("Veli")
                .createdAt(LocalDate.now())
                .build());
    }

    @Test
    void whenCreatedAtIsNull_shouldThrowException() {
        Tckn tckn = new Tckn("12345678901");

        assertThrows(NullPointerException.class, () -> Customer.builder()
                .name("Ali")
                .surname("Veli")
                .tckn(tckn)
                .build());
    }
}