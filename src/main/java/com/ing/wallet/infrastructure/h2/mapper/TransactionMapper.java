package com.ing.wallet.infrastructure.h2.mapper;

import com.ing.wallet.domain.model.Transaction;
import com.ing.wallet.infrastructure.h2.entity.TransactionEntity;
import com.ing.wallet.infrastructure.h2.entity.WalletEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    default TransactionEntity toEntity(Transaction transaction) {
        if (transaction == null) return null;

        TransactionEntity entity = new TransactionEntity();
        entity.setId(transaction.getId());
        entity.setAmount(transaction.getAmount());
        entity.setType(transaction.getType());
        entity.setOppositePartyType(transaction.getOppositePartyType());
        entity.setOppositeParty(transaction.getOppositeParty());
        entity.setStatus(transaction.getStatus());
        entity.setCreatedAt(transaction.getCreatedAt());


        return entity;
    }

    default Transaction toDomain(TransactionEntity entity) {
        if (entity == null) return null;
        return new Transaction(
                entity.getId(),
                entity.getAmount(),
                entity.getType(),
                entity.getOppositePartyType(),
                entity.getOppositeParty(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
