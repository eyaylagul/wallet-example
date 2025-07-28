package com.ing.wallet.domain.model;

import com.ing.wallet.domain.exception.InvalidOperationException;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
public class Transaction {
    public static final BigDecimal PENDING_THRESHOLD = new BigDecimal("1000.00");

    private Long id;
    private final BigDecimal amount;
    private final TransactionType type;
    private final OppositePartyType oppositePartyType;
    private final String oppositeParty;
    private TransactionStatus status;
    private final LocalDate createdAt;

    public Transaction(
            @NonNull BigDecimal amount,
            @NonNull TransactionType type,
            @NonNull OppositePartyType oppositePartyType,
            @NonNull String oppositeParty,
            @NonNull LocalDate createdAt
    ) {
        this.amount = amount;
        this.type = type;
        this.oppositePartyType = oppositePartyType;
        this.oppositeParty = oppositeParty;
        this.status = determineInitialStatus(amount);
        this.createdAt = createdAt;
    }

    public Transaction(
            Long id,
            @NonNull BigDecimal amount,
            @NonNull TransactionType type,
            @NonNull OppositePartyType oppositePartyType,
            @NonNull String oppositeParty,
            @NonNull TransactionStatus status,
            @NonNull LocalDate createdAt
    ) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.oppositePartyType = oppositePartyType;
        this.oppositeParty = oppositeParty;
        this.status = status;
        this.createdAt = createdAt;
    }

    public void approve() {
        if (this.status != TransactionStatus.PENDING)
            throw new InvalidOperationException("Only PENDING transactions can be approved.");
        this.status = TransactionStatus.APPROVED;
    }

    public void deny() {
        if (this.status != TransactionStatus.PENDING)
            throw new InvalidOperationException("Only PENDING transactions can be denied.");
        this.status = TransactionStatus.DENIED;
    }

    private static TransactionStatus determineInitialStatus(BigDecimal amount) {
        return amount.abs().compareTo(PENDING_THRESHOLD) >= 0
                ? TransactionStatus.PENDING
                : TransactionStatus.APPROVED;
    }

}