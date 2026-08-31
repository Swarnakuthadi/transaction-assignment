package com.example.transactionstarter.transaction;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Create transaction
    public Transaction createTransaction(Transaction transaction) {

        // Validation
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        if (transaction.getTransactionId() == null
                || transaction.getTransactionId().isBlank()) {
            throw new IllegalArgumentException("Transaction ID is required");
        }

        if (transaction.getCustomerId() == null
                || transaction.getCustomerId().isBlank()) {
            throw new IllegalArgumentException("Customer ID is required");
        }

        if (transaction.getAmount() == null
                || transaction.getAmount().signum() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (transaction.getCurrency() == null
                || transaction.getCurrency().isBlank()) {
            throw new IllegalArgumentException("Currency is required");
        }

        if (transaction.getTransactionType() == null
                || transaction.getTransactionType().isBlank()) {
            throw new IllegalArgumentException("Transaction type is required");
        }

        if (transaction.getTransactionStatus() == null
                || transaction.getTransactionStatus().isBlank()) {
            throw new IllegalArgumentException("Transaction status is required");
        }

        // Duplicate Transaction ID check
        if (transactionRepository.existsById(transaction.getTransactionId())) {
            throw new IllegalArgumentException(
                    "Transaction ID already exists");
        }

        // Save transaction
        return transactionRepository.save(transaction);
    }

    // Get transaction
    public Transaction getTransaction(String transactionId) {

        return transactionRepository.findById(transactionId)
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Transaction not found"));
    }

    // Update transaction status
    public Transaction updateTransactionStatus(
            String transactionId,
            String status) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Transaction not found"));

        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status is required");
        }

        String currentStatus = transaction.getTransactionStatus();

        // Only PENDING transactions can change status
        if (!"PENDING".equalsIgnoreCase(currentStatus)) {
            throw new IllegalArgumentException(
                    "Only PENDING transactions can change status");
        }

        // Only COMPLETED or FAILED are allowed next
        if (!"COMPLETED".equalsIgnoreCase(status)
                && !"FAILED".equalsIgnoreCase(status)) {
            throw new IllegalArgumentException(
                    "Status can only be changed to COMPLETED or FAILED");
        }

        transaction.setTransactionStatus(status.toUpperCase());

        return transactionRepository.save(transaction);
    }

    // Get all transactions for a customer
    public List<Transaction> getCustomerTransactions(String customerId) {

        return transactionRepository.findByCustomerId(customerId);
    }
}