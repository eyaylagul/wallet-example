package com.ing.wallet.api.dto;

import com.ing.wallet.domain.model.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WalletCreateRequestDto(
        @NotNull Long customerId,
        @NotBlank String walletName,
        @NotNull Currency currency,
        @NotNull Boolean activeForShopping,
        @NotNull Boolean activeForWithdraw
) {}