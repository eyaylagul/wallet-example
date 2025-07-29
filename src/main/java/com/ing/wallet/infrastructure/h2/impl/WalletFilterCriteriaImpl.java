package com.ing.wallet.infrastructure.h2.impl;

import com.ing.wallet.domain.repository.WalletFilterCriteria;
import com.ing.wallet.infrastructure.h2.entity.WalletEntity;
import org.springframework.data.jpa.domain.Specification;


public class WalletFilterCriteriaImpl {

    public static Specification<WalletEntity> byCriteria(WalletFilterCriteria criteria) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (criteria.customerId() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("customer").get("id"), criteria.customerId()));
            }
            if (criteria.currency() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("currency"), criteria.currency()));
            }
            if (criteria.minBalance() != null) {
                predicates = cb.and(predicates, cb.ge(root.get("balance"), criteria.minBalance()));
            }
            if (criteria.maxBalance() != null) {
                predicates = cb.and(predicates, cb.le(root.get("balance"), criteria.maxBalance()));
            }
            if (criteria.activeForShopping() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("activeForShopping"), criteria.activeForShopping()));
            }
            if (criteria.activeForWithdraw() != null) {
                predicates = cb.and(predicates, cb.equal(root.get("activeForWithdraw"), criteria.activeForWithdraw()));
            }
            return predicates;
        };
    }
}
