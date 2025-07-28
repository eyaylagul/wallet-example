package com.ing.wallet.domain.exception;

public abstract class AbstractDomainException extends RuntimeException {
    public AbstractDomainException(String message) {
        super(message);
    }
}