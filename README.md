Toucan Payments - Transaction Processing Service
Overview

This project is a simple transaction processing service built using Java, Spring Boot, Spring Data JPA, and H2 database.

The application supports the following four operations:

Create a transaction
Get a transaction by Transaction ID
Update the status of a transaction
Get all transactions for a Customer ID

I kept the project structure simple using Controller, Service, and Repository layers.

Assumptions and Validation

I did not receive any candidate-specific variant in my invitation email, so I used the following validation and business rules:

Transaction ID is required, cannot be blank, and must be unique.
Transaction ID can have a maximum of 50 characters.
Customer ID is required, cannot be blank, and can have a maximum of 50 characters.
Amount is required, must be greater than 0, and cannot be more than 100,000.00.
Supported currencies are INR, USD, and EUR.
Supported transaction types are PAYMENT, REFUND, and TRANSFER.
Every new transaction starts with PENDING status.
PENDING can be changed to COMPLETED or FAILED.
COMPLETED and FAILED are final statuses and cannot be changed again.
Currency, transaction type, and status are stored in uppercase.

These validation rules are my assumptions because no individual candidate-specific variant was provided in my invitation email.

API Endpoints
1. Create Transaction

POST /api/transactions

Example request:

{
  "transactionId": "TX1001",
  "customerId": "CUST1",
  "amount": 500.00,
  "currency": "INR",
  "transactionType": "PAYMENT"
}

A successful request creates the transaction with PENDING status and returns 201 Created.

2. Get Transaction

GET /api/transactions/{transactionId}

Returns the transaction when it exists and returns 200 OK.

If the transaction does not exist, the API returns 404 Not Found.

3. Update Transaction Status

PATCH /api/transactions/{transactionId}/status

Example request:

{
  "status": "COMPLETED"
}

Allowed status changes are:

PENDING → COMPLETED
PENDING → FAILED

COMPLETED and FAILED are final statuses and cannot be changed again.

A successful update returns 200 OK.

4. Get Customer Transactions

GET /api/customers/{customerId}/transactions

Returns all transactions for the given Customer ID.

If there are no transactions for the customer, an empty list is returned.

Error Handling

The main error cases are handled using appropriate HTTP status codes:

400 Bad Request - Invalid input or invalid status transition
404 Not Found - Transaction does not exist
409 Conflict - Transaction ID already exists

Errors are returned as JSON responses.

Testing

I used both automated tests and manual API testing through Postman.

The automated tests cover the following scenarios:

Successful transaction creation
Invalid transaction data
Duplicate Transaction ID
Transaction not found
Successful status update from PENDING to COMPLETED
Rejection of an invalid status change from COMPLETED to FAILED
Customer transaction lookup

In addition, the starter project contains a Spring application context test to verify that the application starts successfully.

I also manually tested the four APIs and the main negative cases using Postman. I verified the transaction data and status changes using the H2 Console.

The final test suite was executed using:

.\mvnw.cmd clean test

Final test result:

Tests run: 8, Failures: 0, Errors: 0, Skipped: 0

BUILD SUCCESS

The complete test suite passed successfully.

Database

The project uses H2 as an embedded database, so no separate database installation is required.

Database URL:

jdbc:h2:mem:transactions

H2 Console:

http://localhost:8080/h2-console

The database is created when the application starts.

Known Limitations
H2 is used for this coding exercise, so the data is not intended for permanent production storage.
Authentication and authorization are not included.
Pagination is not implemented for customer transactions.
Validation rules are currently fixed in the code.
There is no frontend for the application.
Improvements

If I had more time, I would:

Add more test cases for additional edge cases.
Move validation rules to a separate configuration or class.
Add pagination for customer transactions.
Add Swagger/OpenAPI documentation.
Use MySQL or PostgreSQL for a production application.
Add tests for concurrent transaction requests.
How to Run
Windows

Run the following command from the project root:

.\mvnw.cmd clean test

To run the application from Eclipse, run TransactionStarterApplication as a Spring Boot Application.

The application runs on port 8080.

No separate database setup is required because the project uses H2.

AI Usage Disclosure

AI usage details are documented separately in:

AI_USAGE_DISCLOSURE.md