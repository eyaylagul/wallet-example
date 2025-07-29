package com.ing.wallet.api.dto;

import com.ing.wallet.domain.model.OppositePartyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record TransactionWithdrawRequestDto(
        @NotNull @Positive BigDecimal amount,
        @NotBlank String destination,
        @NotNull OppositePartyType destinationType
) {}
