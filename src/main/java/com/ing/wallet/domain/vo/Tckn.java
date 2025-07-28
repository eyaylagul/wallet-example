package com.ing.wallet.domain.vo;

import lombok.NonNull;

import java.util.Objects;

public record Tckn(@NonNull String value) {
    public Tckn {
        if (!value.matches("\\d{11}"))
            throw new IllegalArgumentException("TCKN must be exactly 11 digits");
    }
}
