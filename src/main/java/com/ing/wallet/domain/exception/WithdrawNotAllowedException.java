package com.ing.wallet.domain.exception;

public class WithdrawNotAllowedException extends AbstractDomainException {
    public WithdrawNotAllowedException() {
        super("Bu cüzdandan para çekmeye izin verilmiyor.");
    }
}