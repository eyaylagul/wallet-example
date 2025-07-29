package com.ing.wallet.domain.exception;

public class InsufficientBalanceException extends AbstractDomainException {
    public InsufficientBalanceException() {
        super("Insufficient Balance ");
    }
}
