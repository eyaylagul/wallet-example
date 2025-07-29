package com.ing.wallet.domain.repository;

import com.ing.wallet.domain.model.Transaction;

public interface TransactionRepository {
    CustomPage<Transaction> all(TransactionFilterCriteria criteria, CustomPageable pageable);
    Transaction save(Transaction transaction);
}