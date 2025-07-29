package com.ing.wallet.infrastructure.h2.mapper;

import com.ing.wallet.domain.model.Wallet;
import com.ing.wallet.infrastructure.h2.entity.WalletEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class WalletMapper {

    private final CustomerMapper customerMapper;
    private final TransactionMapper transactionMapper;

    public WalletMapper(CustomerMapper customerMapper, TransactionMapper transactionMapper) {
        this.customerMapper = customerMapper;
        this.transactionMapper = transactionMapper;
    }

    public Wallet toDomain(WalletEntity entity) {
        if (entity == null) return null;
        return new Wallet(
                entity.getId(),
                customerMapper.toDomain(entity.getCustomer()),
                entity.getWalletName(),
                entity.getCurrency(),
                entity.isActiveForShopping(),
                entity.isActiveForWithdraw(),
                entity.getBalance(),
                entity.getUsableBalance(),
                entity.getTransactions() == null ? null :
                        entity.getTransactions().stream()
                                .map(transactionMapper::toDomain)
                                .collect(Collectors.toList())
        );
    }

    public WalletEntity toEntity(Wallet domain) {
        if (domain == null) return null;
        WalletEntity entity = new WalletEntity();
        entity.setId(domain.getId());
        entity.setCustomer(customerMapper.toEntity(domain.getCustomer()));
        entity.setWalletName(domain.getWalletName());
        entity.setCurrency(domain.getCurrency());
        entity.setActiveForShopping(domain.isActiveForShopping());
        entity.setActiveForWithdraw(domain.isActiveForWithdraw());
        entity.setBalance(domain.getBalance());
        entity.setUsableBalance(domain.getUsableBalance());
        if (domain.getTransactions() != null) {
            var txEntities = domain.getTransactions().stream()
                    .map(transaction -> {
                        var txEntity = transactionMapper.toEntity(transaction);
                        txEntity.setWallet(entity);
                        return txEntity;
                    })
                    .collect(Collectors.toList());
            entity.setTransactions(txEntities);
        } else {
            entity.setTransactions(null);
        }
        return entity;
    }
}