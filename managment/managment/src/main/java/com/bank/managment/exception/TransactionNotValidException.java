package com.bank.managment.exception;

public class TransactionNotValidException extends RuntimeException {
    public TransactionNotValidException(String message) {
        super(message);
    }
}
