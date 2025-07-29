package com.ing.wallet.infrastructure.h2.impl;

import com.ing.wallet.domain.model.Transaction;
import com.ing.wallet.domain.repository.CustomPage;
import com.ing.wallet.domain.repository.CustomPageable;
import com.ing.wallet.domain.repository.TransactionFilterCriteria;
import com.ing.wallet.domain.repository.TransactionRepository;
import com.ing.wallet.infrastructure.h2.JpaTransactionRepository;
import com.ing.wallet.infrastructure.h2.entity.TransactionEntity;
import com.ing.wallet.infrastructure.h2.mapper.TransactionMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final JpaTransactionRepository jpaTransactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionRepositoryImpl(JpaTransactionRepository jpaTransactionRepository, TransactionMapper transactionMapper) {
        this.jpaTransactionRepository = jpaTransactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public CustomPage<Transaction> all(TransactionFilterCriteria criteria, CustomPageable pageable) {
        Specification<TransactionEntity> specification = TransactionFilterCriteriaImpl.byCriteria(criteria);
        var pageRequest = PageRequest.of(pageable.pageNumber(), pageable.pageSize());
        var pageResult = jpaTransactionRepository.findAll(specification, pageRequest);

        List<Transaction> content = pageResult.getContent().stream()
                .map(transactionMapper::toDomain)
                .toList();

        return new CustomPage<>(
                content,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements()
        );
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = transactionMapper.toEntity(transaction);
        TransactionEntity saved = jpaTransactionRepository.save(entity);

        return transactionMapper.toDomain(saved);
    }
}
