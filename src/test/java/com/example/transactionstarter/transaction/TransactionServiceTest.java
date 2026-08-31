package com.example.transactionstarter.transaction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService = new TransactionService(transactionRepository);
    }

    // 1. Transaction should be created successfully
    @Test
    void shouldCreateTransactionSuccessfully() {

        Transaction transaction = new Transaction(
                "TXN001",
                "CUST001",
                new BigDecimal("1000.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        when(transactionRepository.existsById("TXN001"))
                .thenReturn(false);

        when(transactionRepository.save(transaction))
                .thenReturn(transaction);

        Transaction result = transactionService.createTransaction(transaction);

        assertNotNull(result);
        assertEquals("TXN001", result.getTransactionId());
        assertEquals("CUST001", result.getCustomerId());
        assertEquals(new BigDecimal("1000.00"), result.getAmount());

        verify(transactionRepository).save(transaction);
    }

    // 2. Invalid transaction should be rejected
    @Test
    void shouldRejectInvalidTransaction() {

        Transaction transaction = new Transaction(
                "TXN002",
                "CUST001",
                null,
                "INR",
                "PAYMENT",
                "PENDING"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.createTransaction(transaction)
        );

        verify(transactionRepository, never()).save(any());
    }

    // 3. Duplicate transaction ID should be rejected
    @Test
    void shouldRejectDuplicateTransactionId() {

        Transaction transaction = new Transaction(
                "TXN001",
                "CUST001",
                new BigDecimal("500.00"),
                "INR",
                "PAYMENT",
                "PENDING"
        );

        when(transactionRepository.existsById("TXN001"))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> transactionService.createTransaction(transaction)
        );

        verify(transactionRepository, never()).save(any());
    }

    // 4. Non-existing transaction should return an error
    @Test
    void shouldRejectWhenTransactionDoesNotExist() {

        when(transactionRepository.findById("TXN999"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> transactionService.getTransaction("TXN999")
        );

        assertEquals("Transaction not found", exception.getMessage());
    }
}
