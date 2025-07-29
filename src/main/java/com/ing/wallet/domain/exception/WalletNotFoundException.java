package com.ing.wallet.domain.exception;

public class WalletNotFoundException extends AbstractDomainException {
    public WalletNotFoundException(String message) {
        super(message);
    }
}
