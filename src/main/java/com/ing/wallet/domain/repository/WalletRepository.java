package com.ing.wallet.domain.repository;

import com.ing.wallet.domain.model.Wallet;

import java.util.List;
import java.util.Optional;

public interface WalletRepository {
    CustomPage<Wallet> all(WalletFilterCriteria criteria, CustomPageable pageable);
    Optional<Wallet> find(Long id);
    Wallet findOrFail(Long id);
    Wallet save(Wallet wallet);
}