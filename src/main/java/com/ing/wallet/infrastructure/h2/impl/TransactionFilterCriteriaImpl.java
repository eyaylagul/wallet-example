package com.ing.wallet.infrastructure.h2.impl;

import com.ing.wallet.domain.repository.TransactionFilterCriteria;
import com.ing.wallet.infrastructure.h2.entity.TransactionEntity;
import org.springframework.data.jpa.domain.Specification;

public class TransactionFilterCriteriaImpl {

    public static Specification<TransactionEntity> byCriteria(TransactionFilterCriteria criteria) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (criteria.walletId() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("wallet").get("id"), criteria.walletId()));
            }
            if (criteria.type() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("type"), criteria.type()));
            }
            if (criteria.status() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("status"), criteria.status()));
            }
            if (criteria.oppositePartyType() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("oppositePartyType"), criteria.oppositePartyType()));
            }
            return predicates;
        };
    }
}
