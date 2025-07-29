package com.ing.wallet.infrastructure.h2.impl;

import com.ing.wallet.domain.exception.WalletNotFoundException;
import com.ing.wallet.domain.model.Wallet;
import com.ing.wallet.domain.repository.CustomPage;
import com.ing.wallet.domain.repository.CustomPageable;
import com.ing.wallet.domain.repository.WalletFilterCriteria;
import com.ing.wallet.domain.repository.WalletRepository;
import com.ing.wallet.infrastructure.h2.JpaWalletRepository;
import com.ing.wallet.infrastructure.h2.entity.WalletEntity;
import com.ing.wallet.infrastructure.h2.mapper.WalletMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WalletRepositoryImpl implements WalletRepository {

    private final JpaWalletRepository jpaWalletRepository;
    private final WalletMapper walletMapper;

    public WalletRepositoryImpl(JpaWalletRepository jpaWalletRepository, WalletMapper walletMapper) {
        this.jpaWalletRepository = jpaWalletRepository;
        this.walletMapper = walletMapper;
    }

    @Override
    public CustomPage<Wallet> all(WalletFilterCriteria criteria, CustomPageable pageable) {
        Specification<WalletEntity> specification = WalletFilterCriteriaImpl.byCriteria(criteria);
        PageRequest pageRequest = PageRequest.of(pageable.pageNumber(), pageable.pageSize());
        Page<WalletEntity> pageResult = jpaWalletRepository.findAll(specification, pageRequest);

        List<Wallet> content = pageResult.getContent().stream()
                .map(walletMapper::toDomain)
                .toList();

        return new CustomPage<>(
                content,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements()
        );
    }

    @Override
    public Optional<Wallet> find(Long id) {
        return jpaWalletRepository.findById(id).map(walletMapper::toDomain);
    }

    @Override
    public Wallet findOrFail(Long id) {
        return this.find(id)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found: " + id));
    }

    @Override
    public Wallet save(Wallet wallet) {
        WalletEntity entity = walletMapper.toEntity(wallet);
        WalletEntity saved = jpaWalletRepository.save(entity);

        return walletMapper.toDomain(saved);
    }
}