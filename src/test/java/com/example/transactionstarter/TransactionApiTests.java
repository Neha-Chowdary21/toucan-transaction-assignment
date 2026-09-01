package com.example.transactionstarter;

import com.example.transactionstarter.transaction.dto.CreateTransactionRequest;
import com.example.transactionstarter.transaction.dto.UpdateStatusRequest;
import com.example.transactionstarter.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionApiTests {

    @Autowired MockMvc mockMvc;
    @Autowired TransactionRepository repository;

    @BeforeEach
    void cleanDatabase() {
        repository.deleteAll();
    }

    @Test
    void createsTransactionSuccessfully() throws Exception {
        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"transactionId":"TX1001","customerId":"CUST1","amount":500.00,"currency":"INR","transactionType":"PAYMENT"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").value("TX1001"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void rejectsInvalidTransaction() throws Exception {
        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"transactionId":"","customerId":"CUST1","amount":-5,"currency":"INR","transactionType":"PAYMENT"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"));
    }

    @Test
    void rejectsDuplicateTransactionId() throws Exception {
        String json = """
                {"transactionId":"TX1002","customerId":"CUST1","amount":100.00,"currency":"USD","transactionType":"TRANSFER"}
                """;
        mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/transactions").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isConflict());
    }

    @Test
    void returnsNotFoundForMissingTransaction() throws Exception {
        mockMvc.perform(get("/api/transactions/DOES_NOT_EXIST"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updatesPendingTransactionToCompleted() throws Exception {
        create("TX1003");
        mockMvc.perform(patch("/api/transactions/TX1003/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"COMPLETED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void rejectsChangingCompletedTransaction() throws Exception {
        create("TX1004");
        mockMvc.perform(patch("/api/transactions/TX1004/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"COMPLETED\"}"))
                .andExpect(status().isOk());

        mockMvc.perform(patch("/api/transactions/TX1004/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":\"FAILED\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getsCustomerTransactions() throws Exception {
        create("TX1005");
        create("TX1006");
        mockMvc.perform(get("/api/customers/CUST1/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value("CUST1"))
                .andExpect(jsonPath("$.length()").value(2));
    }

    private void create(String id) throws Exception {
        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"transactionId\":\"" + id + "\"," +
                        "\"customerId\":\"CUST1\"," +
                        "\"amount\":500," +
                        "\"currency\":\"INR\"," +
                        "\"transactionType\":\"PAYMENT\"}"))
                .andExpect(status().isCreated());
    }
}
