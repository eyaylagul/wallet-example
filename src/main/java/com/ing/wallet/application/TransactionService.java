package com.ing.wallet.application;

import com.ing.wallet.domain.model.Transaction;
import com.ing.wallet.domain.repository.TransactionFilterCriteria;
import com.ing.wallet.domain.repository.TransactionRepository;
import com.ing.wallet.domain.repository.CustomPage;
import com.ing.wallet.domain.repository.CustomPageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public CustomPage<Transaction> all(TransactionFilterCriteria criteria, CustomPageable pageable) {
        return transactionRepository.all(criteria, pageable);
    }
}
