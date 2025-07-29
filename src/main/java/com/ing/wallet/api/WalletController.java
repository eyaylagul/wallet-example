package com.ing.wallet.api;

import com.ing.wallet.api.dto.*;
import com.ing.wallet.application.WalletService;
import com.ing.wallet.domain.model.Wallet;
import com.ing.wallet.domain.repository.CustomPage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@Valid @RequestBody WalletCreateRequestDto request) {
        return ResponseEntity.ok(walletService.createWallet(request));
    }

    @GetMapping
    public ResponseEntity<CustomPage<Wallet>> listWallets(
            @RequestParam Long customerId,
            @RequestParam(required = false) String currency,
            @RequestParam(required = false) BigDecimal minBalance,
            @RequestParam(required = false) BigDecimal maxBalance,
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "20") @Min(1) Integer size
    ) {
        return ResponseEntity.ok(walletService.all(customerId, currency, minBalance, maxBalance, page, size));
    }


    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<Wallet> deposit(
            @PathVariable Long walletId,
            @Valid @RequestBody TransactionDepositRequestDto request
    ) {
        return ResponseEntity.ok(walletService.deposit(walletId, request));
    }


    @PostMapping("/{walletId}/withdraw")
    public ResponseEntity<Wallet> withdraw(
            @PathVariable Long walletId,
            @Valid @RequestBody TransactionWithdrawRequestDto request
    ) {
        return ResponseEntity.ok(walletService.withdraw(walletId, request));
    }
}