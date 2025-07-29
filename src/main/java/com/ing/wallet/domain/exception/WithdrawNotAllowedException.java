package com.ing.wallet.domain.exception;

public class WithdrawNotAllowedException extends AbstractDomainException {
    public WithdrawNotAllowedException() {
        super("Withdraw Not Allowed");
    }
}