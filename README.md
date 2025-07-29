# 💰 Wallet API

A Spring Boot RESTful API for managing digital wallets and transaction approvals.

Built using:

- ✅ **Domain-Driven Design (DDD)**
- 🧱 **Hexagonal (Ports & Adapters) Architecture**
- 🚀 **Spring Boot 3** with in-memory H2 database

The project follows clean architectural boundaries between:
- Domain logic
- Infrastructure (persistence, security)
- Application services
- REST adapters (controllers)

## 🚀 Features

- Create and list wallets
- Deposit and withdraw money
- Approve or deny high-value transactions
- In-memory H2 database
- Swagger API documentation
- ~~Role-based access control:~~
    - ~~`EMPLOYEE`: full access~~
    - ~~`CUSTOMER`: limited to own wallets~~

---

## 📦 Build & Run

### 🛠 Requirements

- Java 21+
- Gradle (or use included wrapper)

### 🔨 Build
```bash
./gradlew bootRun
```

## 🧪 Default Data

On application startup, **3 default customers** are inserted automatically:

- `customerId = 1`
- `customerId = 2`
- `customerId = 3`

These can be used for creating wallet

---

## 🔗 Useful Endpoints

- **Swagger UI**:  
  [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

- **H2 Console** (in-memory database):  
  [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  - JDBC URL: `jdbc:h2:mem:walletdb`
  - Username: `sa`
  - Password: _(leave empty)_

---

## ✅ Run Tests

To run unit and integration tests:

```bash
./gradlew test