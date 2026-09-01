package com.example.transactionstarter.transaction.exception;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(String from, String to) {
        super("Status change from " + from + " to " + to + " is not allowed");
    }
}
