package com.ing.wallet.application;

import com.ing.wallet.api.dto.*;
import com.ing.wallet.domain.model.*;
import com.ing.wallet.domain.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public WalletService(
            WalletRepository walletRepository,
            CustomerRepository customerRepository,
            TransactionRepository transactionRepository
    ) {
        this.walletRepository = walletRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Wallet createWallet(WalletCreateRequestDto request) {
        Customer customer = customerRepository.findOrFail(request.customerId());
        Wallet wallet = new Wallet(
                null,
                customer,
                request.walletName(),
                request.currency(),
                request.activeForShopping(),
                request.activeForWithdraw(),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                List.of()
        );
        return walletRepository.save(wallet);
    }

    public CustomPage<Wallet> all(Long customerId, String currency, BigDecimal minBalance, BigDecimal maxBalance, Integer page, Integer size) {
        WalletFilterCriteria criteria = new WalletFilterCriteria(
                customerId,
                currency != null ? Currency.valueOf(currency) : null,
                minBalance,
                maxBalance,
                null,
                null
        );
        CustomPageable pageable = new CustomPageable(page, size);

        return walletRepository.all(criteria, pageable);
    }

    @Transactional
    public Wallet deposit(Long walletId, TransactionDepositRequestDto request) {
        Wallet wallet = walletRepository.findOrFail(walletId);
        Transaction tx = wallet.deposit(
                request.amount(),
                request.sourceType(),
                request.source()
        );

        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet withdraw(Long walletId, TransactionWithdrawRequestDto request) {
        Wallet wallet = walletRepository.findOrFail(walletId);
        Transaction tx = wallet.withdraw(
                request.amount(),
                request.destinationType(),
                request.destination()
        );

        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet approveTransaction(Long walletId, Long transactionId) {
        Wallet wallet = walletRepository.findOrFail(walletId);
        wallet.approveTransaction(transactionId);

        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet denyTransaction(Long walletId, Long transactionId) {
        Wallet wallet = walletRepository.findOrFail(walletId);
        wallet.denyTransaction(transactionId);

        return walletRepository.save(wallet);
    }
}