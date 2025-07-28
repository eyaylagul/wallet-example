package com.ing.wallet.domain.model;

import com.ing.wallet.domain.exception.InsufficientBalanceException;
import com.ing.wallet.domain.exception.WithdrawNotAllowedException;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class Wallet {
    private final Long id;
    private final Customer customer;
    private final String walletName;
    private final Currency currency;
    private final boolean activeForShopping;
    private final boolean activeForWithdraw;
    private BigDecimal balance;
    private BigDecimal usableBalance;
    private final List<Transaction> transactions;

    public Wallet(
            Long id,
            @NonNull Customer customer,
            @NonNull String walletName,
            @NonNull Currency currency,
            boolean activeForShopping,
            boolean activeForWithdraw,
            BigDecimal balance,
            BigDecimal usableBalance,
            List<Transaction> transactions
    ) {
        this.id = id;
        this.customer = Objects.requireNonNull(customer, "customer is required");
        this.walletName = Objects.requireNonNull(walletName, "walletName is required");
        this.currency = Objects.requireNonNull(currency, "currency is required");
        this.activeForShopping = activeForShopping;
        this.activeForWithdraw = activeForWithdraw;
        this.balance = balance != null ? balance : BigDecimal.ZERO;
        this.usableBalance = usableBalance != null ? usableBalance : BigDecimal.ZERO;
        this.transactions = transactions != null ? new ArrayList<>(transactions) : new ArrayList<>();
    }

    public Transaction deposit(@NonNull BigDecimal amount, @NonNull OppositePartyType partyType, @NonNull String party) {
        Transaction tx = new Transaction(
                amount,
                TransactionType.DEPOSIT,
                partyType,
                party,
                LocalDate.now()
        );
        this.transactions.add(tx);
        this.balance = this.balance.add(amount);

        if (tx.getStatus() == TransactionStatus.APPROVED) {
            this.usableBalance = this.usableBalance.add(amount);
        }

        return tx;
    }

    public Transaction withdraw(@NonNull BigDecimal amount, @NonNull OppositePartyType partyType, @NonNull String party) {
        if (!this.activeForWithdraw) {
            throw new WithdrawNotAllowedException();
        }
        if (this.usableBalance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }

        Transaction tx = new Transaction(
                amount.negate(),
                TransactionType.WITHDRAW,
                partyType,
                party,
                LocalDate.now()
        );
        this.transactions.add(tx);

        if (tx.getStatus() == TransactionStatus.APPROVED) {
            this.usableBalance = this.usableBalance.subtract(amount);
            this.balance = this.balance.subtract(amount);
        } else if (tx.getStatus() == TransactionStatus.PENDING) {
            this.usableBalance = this.usableBalance.subtract(amount);
        }

        return tx;
    }

    public void approveTransaction(Long transactionId) {
        Transaction tx = findTransactionById(transactionId);
        tx.approve();

        if (tx.getType() == TransactionType.DEPOSIT) {
            this.usableBalance = this.usableBalance.add(tx.getAmount());
        } else if (tx.getType() == TransactionType.WITHDRAW) {
            this.balance = this.balance.subtract(tx.getAmount().abs());
        }
    }

    public void denyTransaction(Long transactionId) {
        Transaction tx = findTransactionById(transactionId);
        tx.deny();

        if (tx.getType() == TransactionType.WITHDRAW) {
            this.usableBalance = this.usableBalance.add(tx.getAmount().abs());
        }
    }

    private Transaction findTransactionById(Long transactionId) {
        return this.transactions.stream()
                .filter(t -> t.getId() != null && t.getId().equals(transactionId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + transactionId));
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(this.transactions);
    }
}