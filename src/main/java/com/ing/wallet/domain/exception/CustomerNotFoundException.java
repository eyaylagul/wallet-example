package com.ing.wallet.domain.exception;

public class CustomerNotFoundException extends AbstractDomainException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
