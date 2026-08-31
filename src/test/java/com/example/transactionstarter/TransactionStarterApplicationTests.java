package com.example.transactionstarter;

import com.example.transactionstarter.transaction.Transaction;
import com.example.transactionstarter.transaction.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TransactionStarterApplicationTests {

    @Autowired
    private TransactionService transactionService;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldCreateTransaction() {

        Transaction transaction = new Transaction();

        transaction.setTransactionId("TEST001");
        transaction.setCustomerId("CUST_TEST");
        transaction.setAmount(new BigDecimal("500.00"));
        transaction.setCurrency("INR");
        transaction.setTransactionType("PAYMENT");
        transaction.setTransactionStatus("PENDING");

        Transaction savedTransaction =
                transactionService.createTransaction(transaction);

        assertEquals("TEST001", savedTransaction.getTransactionId());
        assertEquals("CUST_TEST", savedTransaction.getCustomerId());
        assertEquals(new BigDecimal("500.00"), savedTransaction.getAmount());
        assertEquals("PENDING", savedTransaction.getTransactionStatus());
    }
}