# AI Usage Disclosure

## 1. AI Tool Used

I used ChatGPT as an AI coding assistant during the development of this project.

## 2. How I Used ChatGPT

I used ChatGPT mainly for:

- Understanding Spring Boot and Spring Data JPA concepts.
- Understanding the Controller, Service, Repository, DTO, Entity, and exception handling structure.
- Getting suggestions for transaction validation rules.
- Understanding REST API design and HTTP status codes.
- Troubleshooting compilation and runtime errors.
- Getting suggestions for automated tests.
- Reviewing and improving project documentation.

## 3. AI-Generated Suggestions

ChatGPT provided suggestions and explanations related to:

- Transaction validation.
- Transaction status transition rules.
- REST API implementation.
- Exception handling.
- Automated test scenarios.
- README and project documentation.

I reviewed the suggestions and adapted them to the requirements of the Toucan Payments assignment and the existing starter project.

## 4. My Review, Changes, and Corrections

I did not blindly use AI-generated code.

I reviewed the suggestions, made the necessary changes, and verified the implementation against the project requirements and the existing starter project.

I decided the validation rules used in the application based on the assignment requirement that candidates must define and document their own validation rules.

I also reviewed the suggested API structure and kept the implementation simple using Controller, Service, Repository, DTO, and Entity layers.

During development, I encountered issues such as a Java version mismatch and a port 8080 conflict. I investigated and fixed these issues and verified that the application worked correctly afterward.

I did not identify any specific AI-generated suggestion that was incorrect after reviewing and testing it. Where suggestions were provided, I verified them against the assignment requirements before applying them.

## 5. Testing and Verification

I manually tested the APIs using Postman, including successful requests and important negative cases.

I also verified the transaction data and status changes using the H2 Console.

I verified the following:

- Successful transaction creation.
- Transaction retrieval.
- Customer transaction retrieval.
- Transaction status update.
- Duplicate Transaction ID handling.
- Validation failure handling.
- Transaction not found handling.
- Invalid status transition handling.

I also ran the complete Maven test suite using PowerShell to verify the automated tests.

Final test result:


Tests run: 8
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS

The final implementation was reviewed and tested using automated tests, Postman, PowerShell, and the H2 Console.

The complete automated test suite passed successfully.