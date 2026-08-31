# Customer Transactions Service

A Spring Boot REST API for managing customer transactions.

## 1. Project Overview

This project implements a Customer Transactions Service using:

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 embedded database
- Maven
- JUnit 5
- Mockito

The service supports four main operations:

1. Create a transaction
2. Get a transaction by Transaction ID
3. Update the status of an existing transaction
4. Get all transactions for a Customer ID

## 2. Project Structure

The main transaction functionality is organized into the following layers:

```text
src
├── main
│   ├── java
│   │   └── com.example.transactionstarter
│   │       ├── sample
│   │       │   └── SampleController.java
│   │       ├── transaction
│   │       │   ├── GlobalExceptionHandler.java
│   │       │   ├── Transaction.java
│   │       │   ├── TransactionController.java
│   │       │   ├── TransactionRepository.java
│   │       │   └── TransactionService.java
│   │       └── TransactionStarterApplication.java
│   └── resources
│       └── application.yml
│
└── test
    └── java
        └── com.example.transactionstarter
            ├── transaction
            │   └── TransactionServiceTest.java
            └── TransactionStarterApplicationTests.java
