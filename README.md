Toucan Payments - Transaction Processing Service
1. Project Overview

This project implements a simple transaction processing service using Java, Spring Boot, Spring Data JPA, and an embedded H2 database.

The application supports the following four operations:

Create a transaction
Get a transaction by Transaction ID
Update the status of a transaction
Get all transactions for a Customer ID

The project follows a simple Controller, Service, and Repository layered structure.

2. Assumptions

I did not receive any candidate-specific validation variant in my invitation email. Therefore, I selected and documented the following validation and business rules for this implementation.

Transaction ID is required and cannot be blank.
Transaction ID must be unique.
Transaction ID can have a maximum of 50 characters.
Customer ID is required and cannot be blank.
Customer ID can have a maximum of 50 characters.
Amount is required and must be greater than 0.
Amount cannot be more than 100,000.00.
Supported currencies are INR, USD, and EUR.
Supported transaction types are PAYMENT, REFUND, and TRANSFER.
Every new transaction starts with PENDING status.
Currency, transaction type, and status are stored in uppercase.

These rules are assumptions because no individual candidate-specific variant was provided in the invitation email available to me.

3. Validation and Business Rules
Transaction ID
Required
Cannot be blank
Must be unique
Maximum length of 50 characters
Customer ID
Required
Cannot be blank
Maximum length of 50 characters
Amount
Required
Must be greater than 0
Maximum amount is 100,000.00
Currency

Supported currencies:

INR
USD
EUR
Transaction Type

Supported transaction types:

PAYMENT
REFUND
TRANSFER
Transaction Status

Every newly created transaction starts with PENDING status.

The following status transitions are allowed:

PENDING to COMPLETED

PENDING to FAILED

COMPLETED and FAILED are final statuses and cannot be changed again.

4. API Endpoints
4.1 Create Transaction

Method: POST

Endpoint:

/api/transactions

Example Request:

{
  "transactionId": "TX1001",
  "customerId": "CUST1",
  "amount": 500.00,
  "currency": "INR",
  "transactionType": "PAYMENT"
}

A successful request creates the transaction with PENDING status.

Response:

201 Created
4.2 Get Transaction

Method: GET

Endpoint:

/api/transactions/{transactionId}

Example:

GET /api/transactions/TX1001

If the transaction exists, the API returns:

200 OK

If the transaction does not exist, the API returns:

404 Not Found
4.3 Update Transaction Status

Method: PATCH

Endpoint:

/api/transactions/{transactionId}/status

Example Request:

{
  "status": "COMPLETED"
}

Allowed status changes:

PENDING to COMPLETED
PENDING to FAILED

COMPLETED and FAILED are final statuses and cannot be changed again.

A successful update returns:

200 OK
4.4 Get Customer Transactions

Method: GET

Endpoint:

/api/customers/{customerId}/transactions

Example:

GET /api/customers/CUST1/transactions

The API returns all transactions belonging to the specified customer.

If there are no transactions for the customer, an empty list is returned.

5. Error Handling

The application handles the main error cases using appropriate HTTP status codes.

HTTP Status	Meaning
400 Bad Request	Invalid input or invalid status transition
404 Not Found	Transaction does not exist
409 Conflict	Transaction ID already exists

Errors are returned as JSON responses.

6. Project Structure

The application follows a simple layered architecture:

Controller
    |
    v
Service
    |
    v
Repository
    |
    v
H2 Database

Controller handles HTTP requests and responses.

Service contains the transaction business logic, validation, duplicate checking, and status transition rules.

Repository uses Spring Data JPA to perform database operations.

Entity represents the transaction data stored in the H2 database.

Custom exceptions and a global exception handler are used to provide appropriate error responses.

7. Testing

Both automated tests and manual API testing were performed.

Automated Tests

The automated tests cover the following scenarios:

Successful transaction creation
Invalid transaction data
Duplicate Transaction ID
Transaction not found
Successful status update from PENDING to COMPLETED
Rejection of an invalid status change from COMPLETED to FAILED
Customer transaction lookup
Spring application context startup
Test Execution

The final test suite was executed on Windows using the Maven wrapper:

mvnw.cmd clean test

Final test result:

Tests run: 8
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS

All tests passed successfully.

Manual Testing

The APIs were also manually tested using Postman.

Manual testing included:

Creating a transaction
Getting a transaction by Transaction ID
Updating transaction status
Testing invalid transaction input
Testing duplicate Transaction ID
Testing a non-existing transaction
Getting transactions for a Customer ID
Testing invalid status transitions

Transaction data and status changes were also verified using the H2 Console.

8. Database

The application uses H2 as an embedded database.

No separate database installation is required.

Database URL:

jdbc:h2:mem:transactions

H2 Console:

http://localhost:8080/h2-console

The database is created automatically when the application starts.

9. Known Limitations
H2 is used as an embedded database for this coding exercise and is not intended as permanent production storage.
Authentication and authorization are not implemented because they are outside the scope of the assignment.
Pagination is not implemented for customer transaction retrieval.
Validation rules are currently fixed in the application code.
There is no frontend application.
The application has not been production-hardened for large-scale traffic.
10. Improvements With More Time

If I had more time, I would consider the following improvements:

Add more automated tests for additional edge cases.
Move business validation rules into a separate configuration or validation component.
Add pagination and sorting for customer transaction retrieval.
Add Swagger or OpenAPI documentation.
Replace H2 with MySQL or PostgreSQL for production use.
Add tests for concurrent transaction requests.
Improve structured error response objects.
Add logging and monitoring for important transaction operations.
Add authentication and authorization for production deployment.
11. How to Run
Prerequisites
Java 17 or later
No separate Maven installation is required because the project includes the Maven wrapper.
No separate database installation is required because the project uses H2.
Run Tests

Open PowerShell in the project root directory and run:

mvnw.cmd clean test
Run the Application

Run:

mvnw.cmd spring-boot:run

The application runs on:

http://localhost:8080

The application can also be started from Eclipse by running TransactionStarterApplication as a Spring Boot Application.

12. AI Usage Disclosure

AI coding assistance was used during the development of this assignment.

Detailed AI usage information is documented separately in:

AI_USAGE_DISCLOSURE.md

AI assistance was mainly used to:

Understand the Spring Boot starter project.
Understand the assignment requirements.
Discuss the transaction entity, controller, service, repository, and exception handling structure.
Identify implementation and testing approaches.
Troubleshoot issues during development.
Review and explain Java and Spring Boot concepts.

The generated suggestions were reviewed and adapted during implementation.

The final implementation was verified by running the automated test suite and manually testing the REST APIs using Postman.

13. Submission Verification

Before submission, the following were verified:

Four required transaction operations are implemented.
Input validation and business rules are implemented.
Duplicate Transaction IDs are rejected.
Missing transactions return 404 Not Found.
Invalid status transitions are rejected.
Automated tests pass successfully.
Manual API testing was performed.
README documentation is included.
AI Usage Disclosure is included.
Test output is included in test-output.txt.
The project builds successfully using the Maven wrapper.

Final test status:

Tests run: 8
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS