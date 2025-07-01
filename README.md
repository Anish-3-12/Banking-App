# 🏦 Banking App

A simple banking application built using **Java Spring Boot**, providing functionality for managing accounts and transactions.
This project showcases RESTful APIs for account creation, fund transfer, and transaction tracking.

---

## 📁 Project Structure

```
Banking-App-master/
├── src/
│   ├── main/
│   │   ├── java/com/bankApp/bankingApp/
│   │   │   ├── controller/         # REST API Controllers
│   │   │   ├── dto/                # Data Transfer Objects
│   │   │   ├── entity/             # JPA Entities
│   │   │   ├── exception/          # Custom Exceptions & Handler
│   │   │   ├── mapper/             # Mapper classes
│   │   │   ├── repository/         # Spring Data Repositories
│   │   │   ├── service/            # Service Interfaces
│   │   │   └── service/impl/       # Service Implementations
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml                        # Maven configuration
└── mvnw / mvnw.cmd                # Maven wrapper
```

---

## 🚀 Features

- Create and manage bank accounts
- Transfer funds between accounts
- View transaction history
- RESTful API architecture
- Centralized exception handling
- Layered Spring Boot architecture

---

## ⚙️ Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA
- MySQL
- Maven

---

###  Clone the Repository
```
https://github.com/Anish-3-12/Banking-App.git
```
---

## 📌 Example Endpoints

| Method | Endpoint                        | Description              |
|--------|----------------------------------|--------------------------|
| GET    | `/accounts`                     | List all accounts        |
| POST   | `/accounts`                     | Create new account       |
| POST   | `/accounts/transfer`            | Transfer funds           |
| GET    | `/accounts/{id}`                | Get account by ID        |
| GET    | `/accounts/{id}/transactions`   | Get transaction history  |

---

## 🧪 Running Tests

```bash
./mvnw test
```
---

## 👨‍💻 Author

**Anish Sabale** – [@Anish-3-12](https://github.com/Anish-3-12)
