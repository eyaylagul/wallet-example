package com.ing.wallet.api.dto;

import com.ing.wallet.domain.model.TransactionStatus;
import jakarta.validation.constraints.NotNull;

public record TransactionUpdateStatusDto(
        @NotNull TransactionStatus status
) {}
