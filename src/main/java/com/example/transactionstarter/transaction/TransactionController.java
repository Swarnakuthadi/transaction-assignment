package com.example.transactionstarter.transaction;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/api/transactions")
    public Transaction createTransaction(
            @RequestBody Transaction transaction) {

        return transactionService.createTransaction(transaction);
    }
    @GetMapping("/api/transactions/{transactionId}")
    public Transaction getTransaction(
        @PathVariable String transactionId) {

    return transactionService.getTransaction(transactionId);
   }
   @PatchMapping("/api/transactions/{transactionId}/status")
  public Transaction updateTransactionStatus(
        @PathVariable String transactionId,
        @RequestParam String status) {

    return transactionService.updateTransactionStatus(
            transactionId,
            status
    );
   }
   @GetMapping("/api/customers/{customerId}/transactions")
   public java.util.List<Transaction> getCustomerTransactions(
        @PathVariable String customerId) {

    return transactionService.getCustomerTransactions(customerId);
    }
}