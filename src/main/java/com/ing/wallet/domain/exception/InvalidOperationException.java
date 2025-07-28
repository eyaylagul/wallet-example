package com.ing.wallet.domain.exception;

public class InvalidOperationException extends AbstractDomainException {
    public InvalidOperationException(String message) {
        super(message);
    }
}
