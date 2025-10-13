# ABC-BANK

A Spring Boot RESTful API application for basic banking operations including account management, deposits, withdrawals,
and transfers.

## Overview

ABC-BANK is a demonstration banking application that provides core banking functionalities through REST APIs. The
application uses an H2 in-memory database for data persistence and includes comprehensive transaction management with
proper locking mechanisms to prevent race conditions.

## Technologies Used

- **Java 21** - Programming language
- **Spring Boot 3.5.6** - Application framework
- **Spring Data JPA** - Data persistence
- **Hibernate 6.6.29** - ORM framework
- **H2 Database** - In-memory/file-based database
- **Lombok** - Boilerplate code reduction
- **ModelMapper 3.2.5** - Object mapping
- **SpringDoc OpenAPI 2.8.13** - API documentation (Swagger)
- **Thymeleaf** - Template engine
- **JUnit 5** - Testing framework
- **JaCoCo 0.8.14** - Code coverage
- **Maven** - Build tool

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

## Build and Installation

### Clone the repository

```bash
git clone <repository-url>
cd ABC-BANK
```

### Build the project

```bash
mvn clean install
```

### Run tests

```bash
mvn test
```

### Run the application

```bash
mvn spring-boot:run
```

Alternatively, you can run the JAR file:

```bash
java -jar target/ABC-BANK-0.0.1-SNAPSHOT.jar
```

The application will start on port **8081**.

## Configuration

The application is configured through `src/main/resources/application.properties`:

- **Server Port**: 8081
- **Database**: H2 (file-based at ~/db/abcbank.data)
- **H2 Console**: Enabled at http://localhost:8081/h2-console
- **Database Credentials**:
    - Username: naruto
    - Password: naruto

## API Documentation

Once the application is running, you can access:

- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8081/v3/api-docs

## API Endpoints

### Account Management

#### Get All Accounts

```
GET /accounts
```

#### Get Account by Account Number

```
GET /accounts/{accountNumber}
```

#### Create New Account

```
POST /accounts
Content-Type: application/json

{
  "acountHolder": "John Doe",
  "balance": 1000.00
}
```

### Transaction Operations

#### Withdraw from Account

```
POST /withdraw
Content-Type: application/json

{
  "fromAccountNumber": "ABC_0000000001",
  "withdrawlAmount": 100.00
}
```

#### Deposit to Account

```
POST /deposit
Content-Type: application/json

{
  "depositorAccountNumber": "ABC_0000000001",
  "depositAmount": 500.00
}
```

#### Transfer Between Accounts

```
POST /transfer
Content-Type: application/json

{
  "fromAccountNumber": "ABC_0000000001",
  "toAccountNumber": "ABC_0000000002",
  "transferAmount": 200.00
}
```

#### Get Transaction History

```
GET /transaction-history
```

#### Get Account Transaction History

```
GET /account-transaction-history
```

## Database Access

### H2 Console

The H2 console is available at: http://localhost:8081/h2-console

**Connection Details:**

- JDBC URL: `jdbc:h2:file:~/db/abcbank.data`
- Username: `naruto`
- Password: `naruto`

## Project Structure

```
ABC-BANK/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/hendisantika/abcbank/
│   │   │       ├── config/          # Configuration classes
│   │   │       │   ├── SwaggerConfig.java
│   │   │       │   ├── TransactionLock.java
│   │   │       │   └── WebConfig.java
│   │   │       ├── controller/      # REST controllers
│   │   │       │   ├── AccountController.java
│   │   │       │   └── TransactionController.java
│   │   │       ├── entity/          # JPA entities
│   │   │       │   ├── Account.java
│   │   │       │   └── Transaction.java
│   │   │       ├── exception/       # Exception handlers
│   │   │       │   ├── GlobalExceptionHandler.java
│   │   │       │   └── GlobalResponseEntityExceptionHandler.java
│   │   │       ├── model/           # DTOs and models
│   │   │       ├── repository/      # JPA repositories
│   │   │       │   ├── AccountRepository.java
│   │   │       │   └── TransactionRepository.java
│   │   │       ├── service/         # Business logic
│   │   │       │   ├── AccountService.java
│   │   │       │   └── AccountTransactionService.java
│   │   │       ├── util/            # Utility classes
│   │   │       └── AbcBankApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── data.sql             # Database schema
│   │       └── logback.xml          # Logging configuration
│   └── test/
│       └── java/                    # Unit and integration tests
├── pom.xml
└── README.md
```

## Features

### Transaction Management

- Thread-safe transaction processing with deadlock prevention
- Ordered locking mechanism for transfer operations
- Automatic account number generation using sequences
- Transaction history tracking (DEBIT/CREDIT)

### Validation

- Insufficient balance validation
- Input validation using Bean Validation
- Global exception handling

### Logging

- Comprehensive logging with Logback
- File-based logging with rotation
- Console and file appenders

## Testing

The project includes comprehensive unit and integration tests:

```bash
# Run all tests
mvn test

# Run tests with coverage report
mvn clean test jacoco:report
```

Test coverage reports are generated in `target/site/jacoco/index.html`.

## Code Coverage

JaCoCo is configured to exclude:

- Application main class
- Entity classes
- Model/DTO classes

## Development

### DevTools

Spring Boot DevTools is included for development convenience:

- Automatic restart on code changes
- LiveReload support
- Enhanced development experience

### Lombok

Lombok is used to reduce boilerplate code. Make sure your IDE has Lombok plugin installed:

- IntelliJ IDEA: Install Lombok plugin and enable annotation processing
- Eclipse: Install Lombok and run lombok.jar

## Known Issues

### Fixed Issues

- **Lombok Compilation**: Maven compiler plugin now includes proper annotation processor configuration
- **BigDecimal Test Assertions**: Tests now use `compareTo()` for BigDecimal comparisons instead of `equals()`

## Future Enhancements

- Add authentication and authorization (Spring Security)
- Implement pagination for transaction history
- Add support for different account types
- Implement transaction rollback mechanisms
- Add scheduled jobs for interest calculation
- Implement audit logging

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## Author

Hendi Santika

- Email: hendisantika@gmail.com
- Telegram: @hendisantika34

## License

This project is created for educational and demonstration purposes.
