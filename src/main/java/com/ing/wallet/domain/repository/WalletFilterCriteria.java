package com.ing.wallet.domain.repository;

import com.ing.wallet.domain.model.Currency;

import java.math.BigDecimal;

public record WalletFilterCriteria(
        Long customerId,
        Currency currency,
        BigDecimal minBalance,
        BigDecimal maxBalance,
        Boolean activeForShopping,
        Boolean activeForWithdraw
) {}
