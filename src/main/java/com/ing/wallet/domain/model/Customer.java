package com.ing.wallet.domain.model;

import com.ing.wallet.domain.vo.Tckn;
import lombok.*;

import java.time.LocalDate;

public record Customer(
        Long id,
        @NonNull String name,
        @NonNull String surname,
        @NonNull Tckn tckn,
        @NonNull LocalDate createdAt) {}
