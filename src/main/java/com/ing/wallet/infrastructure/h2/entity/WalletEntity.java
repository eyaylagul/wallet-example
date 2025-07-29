package com.ing.wallet.infrastructure.h2.entity;

import com.ing.wallet.domain.model.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "wallets")
public class WalletEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    private String walletName;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private boolean isActiveForShopping;
    private boolean isActiveForWithdraw;
    private BigDecimal balance;
    private BigDecimal usableBalance;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionEntity> transactions;
}