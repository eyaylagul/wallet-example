package com.ing.wallet.api;

import com.ing.wallet.api.dto.TransactionListRequestDto;
import com.ing.wallet.application.TransactionService;
import com.ing.wallet.domain.model.Transaction;
import com.ing.wallet.domain.repository.TransactionFilterCriteria;
import com.ing.wallet.domain.repository.CustomPage;
import com.ing.wallet.domain.repository.CustomPageable;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @GetMapping
    public ResponseEntity<CustomPage<Transaction>> all(
            @Valid @ModelAttribute TransactionListRequestDto req
    ) {
        TransactionFilterCriteria criteria = new TransactionFilterCriteria(
                req.walletId(),
                req.type(),
                req.status(),
                req.oppositeParty()
        );

        int page = req.page() != null ? req.page() : 0;
        int size = req.size() != null ? req.size() : 20;
        CustomPageable pageable = new CustomPageable(page, size);

        CustomPage<Transaction> result = transactionService.all(criteria, pageable);

        return ResponseEntity.ok(result);
    }
}