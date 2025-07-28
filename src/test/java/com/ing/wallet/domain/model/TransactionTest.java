package com.ing.wallet.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ing.wallet.domain.exception.InvalidOperationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void whenAmountBelowThreshold_StatusShouldBeApproved() {
        Transaction tx = new Transaction(
                1L,
                new BigDecimal("800"),
                TransactionType.DEPOSIT,
                OppositePartyType.IBAN,
                "TR123",
                LocalDate.now()
        );
        assertEquals(TransactionStatus.APPROVED, tx.getStatus());
    }

    @Test
    void whenAmountAboveThreshold_StatusShouldBePending() {
        Transaction tx = new Transaction(
                2L,
                new BigDecimal("2000"),
                TransactionType.DEPOSIT,
                OppositePartyType.PAYMENT,
                "PAYID123",
                LocalDate.now()
        );
        assertEquals(TransactionStatus.PENDING, tx.getStatus());
    }

    @Test
    void whenAmountEqualThreshold_StatusShouldBePending() {
        Transaction tx = new Transaction(
                3L,
                new BigDecimal("1000"),
                TransactionType.DEPOSIT,
                OppositePartyType.PAYMENT,
                "PAYID111",
                LocalDate.now()
        );
        assertEquals(TransactionStatus.PENDING, tx.getStatus());
    }

    @Test
    void approve_shouldChangeStatusToApproved() {
        Transaction tx = new Transaction(
                4L,
                new BigDecimal("1500"),
                TransactionType.DEPOSIT,
                OppositePartyType.IBAN,
                "TR321",
                LocalDate.now()
        );
        assertEquals(TransactionStatus.PENDING, tx.getStatus());
        tx.approve();
        assertEquals(TransactionStatus.APPROVED, tx.getStatus());
    }

    @Test
    void approve_nonPending_shouldThrow() {
        Transaction tx = new Transaction(
                5L,
                new BigDecimal("200"),
                TransactionType.DEPOSIT,
                OppositePartyType.IBAN,
                "TR000",
                LocalDate.now()
        );
        assertEquals(TransactionStatus.APPROVED, tx.getStatus());
        assertThrows(InvalidOperationException.class, tx::approve);
    }

    @Test
    void deny_shouldChangeStatusToDenied() {
        Transaction tx = new Transaction(
                6L,
                new BigDecimal("1200"),
                TransactionType.DEPOSIT,
                OppositePartyType.PAYMENT,
                "PAYID111",
                LocalDate.now()
        );
        assertEquals(TransactionStatus.PENDING, tx.getStatus());
        tx.deny();
        assertEquals(TransactionStatus.DENIED, tx.getStatus());
    }
}