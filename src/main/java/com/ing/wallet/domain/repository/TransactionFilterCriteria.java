package com.ing.wallet.domain.repository;

import com.ing.wallet.domain.model.OppositePartyType;
import com.ing.wallet.domain.model.TransactionStatus;
import com.ing.wallet.domain.model.TransactionType;

public record TransactionFilterCriteria(
        Long walletId,
        TransactionType type,
        TransactionStatus status,
        OppositePartyType oppositePartyType
) {}
