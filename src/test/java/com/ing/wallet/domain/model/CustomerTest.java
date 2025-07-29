package com.ing.wallet.domain.model;

import com.ing.wallet.domain.vo.Tckn;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void withValidFields_shouldCreateCustomer() {
        Tckn tckn = new Tckn("12345678901");

        Customer customer = new Customer(1L, "Ali", "Veli", tckn, LocalDate.now());


        assertEquals("Ali", customer.name());
        assertEquals("Veli", customer.surname());
        assertEquals(tckn.value(), customer.tckn().value());
        assertNotNull(customer.createdAt());
    }

    @Test
    void whenNameIsNull_shouldThrowException() {
        Tckn tckn = new Tckn("12345678901");

        assertThrows(NullPointerException.class, () -> new Customer(null, null, "Veli", tckn, LocalDate.now()));
    }

    @Test
    void whenSurnameIsNull_shouldThrowException() {
        Tckn tckn = new Tckn("12345678901");

        assertThrows(NullPointerException.class, () -> new Customer(null, "Ali", null, tckn, LocalDate.now()));
    }

    @Test
    void whenTcknIsNull_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> new Customer(null, "Ali", "Veli", null, LocalDate.now()));
    }

    @Test
    void whenCreatedAtIsNull_shouldThrowException() {
        Tckn tckn = new Tckn("12345678901");

        assertThrows(NullPointerException.class, () -> new Customer(null, "Ali", "Veli", tckn, null));
    }
}