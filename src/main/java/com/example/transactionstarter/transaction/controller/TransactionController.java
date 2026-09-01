package com.example.transactionstarter.transaction.controller;

import com.example.transactionstarter.transaction.dto.CreateTransactionRequest;
import com.example.transactionstarter.transaction.dto.TransactionResponse;
import com.example.transactionstarter.transaction.dto.UpdateStatusRequest;
import com.example.transactionstarter.transaction.entity.Transaction;
import com.example.transactionstarter.transaction.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse create(@Valid @RequestBody CreateTransactionRequest request) {
        return TransactionResponse.from(service.create(request));
    }

    @GetMapping("/transactions/{transactionId}")
    public TransactionResponse get(@PathVariable @NotBlank String transactionId) {
        return TransactionResponse.from(service.getByTransactionId(transactionId));
    }

    @PatchMapping("/transactions/{transactionId}/status")
    public TransactionResponse updateStatus(
            @PathVariable @NotBlank String transactionId,
            @Valid @RequestBody UpdateStatusRequest request) {
        return TransactionResponse.from(service.updateStatus(transactionId, request.getStatus()));
    }

    @GetMapping("/customers/{customerId}/transactions")
    public List<TransactionResponse> getCustomerTransactions(
            @PathVariable @NotBlank String customerId) {
        return service.getByCustomerId(customerId).stream()
                .map(TransactionResponse::from)
                .toList();
    }
}
