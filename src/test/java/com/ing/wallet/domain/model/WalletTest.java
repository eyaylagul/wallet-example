package com.ing.wallet.domain.model;

import com.ing.wallet.domain.exception.InsufficientBalanceException;
import com.ing.wallet.domain.vo.Tckn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    private Wallet wallet;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(1L, "Ali", "Veli", new Tckn("12345678901"), LocalDate.now());

        wallet = new Wallet(
                1L,
                customer,
                "Wallet 1",
                Currency.TRY,
                true,
                true,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                new ArrayList<>()
        );
    }

    @Test
    void depositBelowThreshold_shouldIncreaseBothBalances() {
        Transaction tx = wallet.deposit(
                new BigDecimal("500"),
                OppositePartyType.IBAN,
                "TR123"
        );
        assertEquals(TransactionStatus.APPROVED, tx.getStatus());
        assertEquals(new BigDecimal("500"), wallet.getBalance());
        assertEquals(new BigDecimal("500"), wallet.getUsableBalance());
        assertEquals(1, wallet.getTransactions().size());
    }

    @Test
    void depositEqualsThreshold_shouldBePending() {
        Transaction tx = wallet.deposit(
                new BigDecimal("1000"),
                OppositePartyType.PAYMENT,
                "PAY999"
        );
        assertEquals(TransactionStatus.PENDING, tx.getStatus());
        assertEquals(new BigDecimal("1000"), wallet.getBalance());
        assertEquals(BigDecimal.ZERO, wallet.getUsableBalance());
    }

    @Test
    void depositAboveThreshold_shouldBePending() {
        Transaction tx = wallet.deposit(
                new BigDecimal("2000"),
                OppositePartyType.PAYMENT,
                "PAY111"
        );
        assertEquals(TransactionStatus.PENDING, tx.getStatus());
        assertEquals(new BigDecimal("2000"), wallet.getBalance());
        assertEquals(BigDecimal.ZERO, wallet.getUsableBalance());
    }

    @Test
    void withdrawBelowThreshold_shouldDecreaseBothBalances() {
        wallet.deposit(new BigDecimal("800"), OppositePartyType.IBAN, "TR111"); // usable: 800
        Transaction tx = wallet.withdraw(new BigDecimal("500"), OppositePartyType.PAYMENT, "PAY333");
        assertEquals(TransactionStatus.APPROVED, tx.getStatus());
        assertEquals(new BigDecimal("300"), wallet.getBalance());
        assertEquals(new BigDecimal("300"), wallet.getUsableBalance());
        assertEquals(2, wallet.getTransactions().size());
    }

    @Test
    void withdrawAboveThreshold_shouldBePendingAndDecreaseUsableOnly() {
        wallet.deposit(new BigDecimal("3000"), OppositePartyType.IBAN, "TR888"); // pending, usable: 0
        wallet.deposit(new BigDecimal("900"), OppositePartyType.IBAN, "TR999"); // approved, usable: 900

        Transaction tx = wallet.withdraw(new BigDecimal("900"), OppositePartyType.PAYMENT, "PAY444");
        assertEquals(TransactionStatus.APPROVED, tx.getStatus());
        assertEquals(new BigDecimal("3000"), wallet.getBalance());
        assertEquals(BigDecimal.ZERO, wallet.getUsableBalance());
    }

    @Test
    void withdrawPending_shouldDecreaseUsableOnly() {
        BigDecimal firstPendingDepositAmount = new BigDecimal("2500");
        BigDecimal secondPendingDepositAmount = new BigDecimal("1000");

        BigDecimal thirdApprovedDepositAmount = new BigDecimal("1200");

        BigDecimal totalBalance = firstPendingDepositAmount
                .add(secondPendingDepositAmount)
                .add(thirdApprovedDepositAmount);

        BigDecimal totalUsableBalance = thirdApprovedDepositAmount;

        BigDecimal withdrawAmount = new BigDecimal("1100");

        wallet.deposit(firstPendingDepositAmount, OppositePartyType.IBAN, "TR888"); // pending, usable: 0
        wallet.deposit(secondPendingDepositAmount, OppositePartyType.IBAN, "TR999"); // pending, usable: 0
        Transaction approvedTx = wallet.deposit(thirdApprovedDepositAmount, OppositePartyType.IBAN, "TR124");

        setTransactionId(approvedTx, 1L);
        wallet.approveTransaction(1L); // approved, usable: 800


        Transaction tx = wallet.withdraw(withdrawAmount, OppositePartyType.PAYMENT, "PAY555");
        assertEquals(TransactionStatus.PENDING, tx.getStatus());
        assertEquals(totalBalance, wallet.getBalance());
        assertEquals(totalUsableBalance.subtract(withdrawAmount), wallet.getUsableBalance());
    }

    @Test
    void approvePendingDeposit_shouldIncreaseUsableBalance() {
        Transaction tx = wallet.deposit(new BigDecimal("2000"), OppositePartyType.IBAN, "TR999"); // pending
        assertEquals(TransactionStatus.PENDING, tx.getStatus());
        assertEquals(BigDecimal.ZERO, wallet.getUsableBalance());

        setTransactionId(tx, 99L);
        wallet.approveTransaction(99L);
        assertEquals(TransactionStatus.APPROVED, tx.getStatus());
        assertEquals(new BigDecimal("2000"), wallet.getUsableBalance());
        assertEquals(new BigDecimal("2000"), wallet.getBalance());
    }

    @Test
    void approvePendingWithdraw_shouldUpdateBalance() {
        wallet.deposit(new BigDecimal("3000"), OppositePartyType.IBAN, "TRX"); // pending
        wallet.deposit(new BigDecimal("1000"), OppositePartyType.IBAN, "TRY"); // pending
        Transaction txApproved = wallet.deposit(new BigDecimal("1500"), OppositePartyType.IBAN, "TRZ"); // approved usable: 800

        setTransactionId(txApproved, 32L);
        wallet.approveTransaction(32L);

        Transaction txPending = wallet.withdraw(new BigDecimal("1200"), OppositePartyType.PAYMENT, "PAY777"); // pending, usable: 300

        assertEquals(new BigDecimal("300"), wallet.getUsableBalance());
        assertEquals(new BigDecimal("5500"), wallet.getBalance());

        setTransactionId(txPending, 123L);
        wallet.approveTransaction(123L);

        assertEquals(TransactionStatus.APPROVED, txPending.getStatus());

        assertEquals(new BigDecimal("300"), wallet.getUsableBalance());
        assertEquals(new BigDecimal("4300"), wallet.getBalance());
    }

    @Test
    void denyPendingWithdraw_shouldRestoreUsableBalance() {
        wallet.deposit(new BigDecimal("1200"), OppositePartyType.IBAN, "TRX"); // pending
        Transaction approvedDepositTx = wallet.deposit(new BigDecimal("1800"), OppositePartyType.IBAN, "TRY");  // approved
        setTransactionId(approvedDepositTx, 123L);
        wallet.approveTransaction(123L);

        Transaction tx = wallet.withdraw(new BigDecimal("1000"), OppositePartyType.PAYMENT, "PAY555"); // pending, usable: -1000

        setTransactionId(tx, 77L);
        wallet.denyTransaction(77L);

        assertEquals(TransactionStatus.DENIED, tx.getStatus());
        assertEquals(new BigDecimal("1800"), wallet.getUsableBalance());
    }

    @Test
    void withdrawWithInsufficientBalance_shouldThrowException() {
        assertThrows(InsufficientBalanceException.class, () -> wallet.withdraw(
                new BigDecimal("100"),
                OppositePartyType.IBAN,
                "TR333"
        ));
    }

    private void setTransactionId(Transaction tx, Long id) {
        try {
            var field = Transaction.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(tx, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}