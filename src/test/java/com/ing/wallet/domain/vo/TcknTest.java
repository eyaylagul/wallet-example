package com.ing.wallet.domain.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TcknTest {

    @Test
    void validTckn_shouldCreateTckn () {
        Tckn tckn = new Tckn("12345678901");

        assertEquals("12345678901", tckn.value());
    }

    @Test
    void invalidTckn_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Tckn("test_123"));
        assertThrows(IllegalArgumentException.class, () -> new Tckn("12345"));
    }

    @Test
    void nullTckn_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> new Tckn(null));
    }
}