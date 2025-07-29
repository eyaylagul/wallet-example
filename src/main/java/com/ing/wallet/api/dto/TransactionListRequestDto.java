package com.ing.wallet.api.dto;

import com.ing.wallet.domain.model.OppositePartyType;
import com.ing.wallet.domain.model.TransactionStatus;
import com.ing.wallet.domain.model.TransactionType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record TransactionListRequestDto(
        Long walletId,
        TransactionType type,
        TransactionStatus status,
        OppositePartyType oppositeParty,
        @Min(0)
        Integer page,
        @Min(1) @Max(100)
        Integer size
) {}