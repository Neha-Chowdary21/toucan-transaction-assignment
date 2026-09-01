package com.example.transactionstarter.transaction.service;

import com.example.transactionstarter.transaction.dto.CreateTransactionRequest;
import com.example.transactionstarter.transaction.entity.Transaction;
import com.example.transactionstarter.transaction.exception.DuplicateTransactionException;
import com.example.transactionstarter.transaction.exception.InvalidStatusTransitionException;
import com.example.transactionstarter.transaction.exception.TransactionNotFoundException;
import com.example.transactionstarter.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
public class TransactionService {

    private static final BigDecimal MAX_AMOUNT = new BigDecimal("100000.00");
    private static final List<String> ALLOWED_CURRENCIES = List.of("INR", "USD", "EUR");
    private static final List<String> ALLOWED_TYPES = List.of("PAYMENT", "REFUND", "TRANSFER");

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Transaction create(CreateTransactionRequest request) {
        String transactionId = request.getTransactionId().trim();
        String customerId = request.getCustomerId().trim();
        String currency = request.getCurrency().trim().toUpperCase(Locale.ROOT);
        String type = request.getTransactionType().trim().toUpperCase(Locale.ROOT);

        if (repository.existsByTransactionId(transactionId)) {
            throw new DuplicateTransactionException(transactionId);
        }
        if (request.getAmount().compareTo(MAX_AMOUNT) > 0) {
            throw new IllegalArgumentException("Amount must not exceed 100000.00");
        }
        if (!ALLOWED_CURRENCIES.contains(currency)) {
            throw new IllegalArgumentException("Currency must be INR, USD or EUR");
        }
        if (!ALLOWED_TYPES.contains(type)) {
            throw new IllegalArgumentException("Transaction type must be PAYMENT, REFUND or TRANSFER");
        }

        return repository.save(new Transaction(
                transactionId, customerId, request.getAmount(), currency, type, "PENDING"));
    }

    @Transactional(readOnly = true)
    public Transaction getByTransactionId(String transactionId) {
        return repository.findByTransactionId(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));
    }

    @Transactional
    public Transaction updateStatus(String transactionId, String requestedStatus) {
        Transaction transaction = getByTransactionId(transactionId);
        String newStatus = requestedStatus.trim().toUpperCase(Locale.ROOT);
        String currentStatus = transaction.getStatus();

        if (!List.of("COMPLETED", "FAILED").contains(newStatus)) {
            throw new IllegalArgumentException("Status must be COMPLETED or FAILED");
        }
        if (!"PENDING".equals(currentStatus) || currentStatus.equals(newStatus)) {
            throw new InvalidStatusTransitionException(currentStatus, newStatus);
        }

        transaction.setStatus(newStatus);
        return repository.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<Transaction> getByCustomerId(String customerId) {
        return repository.findByCustomerIdOrderByTransactionIdAsc(customerId.trim());
    }
}
