package com.example.transactionstarter.transaction.dto;

import com.example.transactionstarter.transaction.entity.Transaction;

import java.math.BigDecimal;

public class TransactionResponse {

    private String transactionId;
    private String customerId;
    private BigDecimal amount;
    private String currency;
    private String transactionType;
    private String status;

    public TransactionResponse() {}

    public TransactionResponse(String transactionId, String customerId, BigDecimal amount,
                               String currency, String transactionType, String status) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.amount = amount;
        this.currency = currency;
        this.transactionType = transactionType;
        this.status = status;
    }

    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(
                transaction.getTransactionId(),
                transaction.getCustomerId(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getTransactionType(),
                transaction.getStatus()
        );
    }

    public String getTransactionId() { return transactionId; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getTransactionType() { return transactionType; }
    public String getStatus() { return status; }
}
