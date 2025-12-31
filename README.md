
https://github.com/user-attachments/assets/6e183dd3-e1b1-47b4-85bc-dcb971ba08e6


---

```md
# Loan Accounting & Transaction Management System

A Spring Boot–based backend system demonstrating **transaction-safe accounting**, **clean domain separation**, and **SOLID (SRP & OCP) compliant architecture** using Java, Spring Boot, JPA/Hibernate, and PostgreSQL.

---

## 📌 Project Overview

This project manages **loan disbursement**, **loan ledger entries**, and **accounting transactions** in a consistent and transactional manner.

The system is designed to reflect **real-world enterprise backend practices**, with a strong focus on:

- **SRP (Single Responsibility Principle)**
- **OCP (Open/Closed Principle)**
- Clean REST API design
- Transactional data consistency
- Maintainable and extensible codebase

This project was developed to demonstrate backend engineering capability during an interview process.

---

## 🛠️ Technology Stack

- **Language:** Java  
- **Framework:** Spring Boot  
- **ORM:** JPA / Hibernate  
- **Database:** PostgreSQL  
- **Build Tool:** Maven  
- **API Style:** RESTful APIs  

---

## ✨ Key Features

- Loan Disbursement management
- Loan Ledger debit/credit tracking
- Accounting Transactions handling
- Transaction-safe update and delete operations
- Hibernate dirty checking
- Precise financial calculations using `BigDecimal`
- Clean domain-driven architecture
- Strict SRP-compliant services & controllers
- Easily extensible (OCP-friendly) design

---

## 🧱 Architecture Design (SRP & OCP Applied)

### 🔹 Domain-Driven Services (SRP)

Each service handles **only one domain responsibility**:

- **DisburseService** → Loan disbursement logic
- **LoanLedgerService** → Ledger debit/credit logic
- **TransactionsService** → Accounting transaction logic
- **StaticService** → Demo / bootstrap data only

### 🔹 Domain-Driven Controllers (SRP)

Each controller exposes APIs for **one resource only**:

- **DisburseController**
- **LoanLedgerController**
- **TransactionsController**
- **StaticController** (DEV / TEST only)

This ensures:
- Clear separation of concerns
- Safer future changes
- Minimal impact when extending features (OCP)

---

## 🧱 Database Design

### Tables / Entities

- **Disburse**
- **LoanLedger**
- **Transactions**

### Design Highlights

- Proper primary key usage
- Indexed frequently queried columns
- Transaction-safe multi-table operations
- Prevention of partial updates
- Accurate monetary handling using `BigDecimal`

---

## 🌐 API Endpoints

### 🔹 Base Paths

```

/api/disburse


/api/loan-ledger


/api/transactions


/api/static



````

---

## 🔹 Disburse APIs

- `POST /api/disburse`  
  Create a loan disbursement record

- `PUT /api/disburse/fix-amount?memberId={id}&amount={value}`  
  Update/fix disbursement amount for a member

- `GET /api/disburse`  
  Retrieve all disbursement records

---

## 🔹 Loan Ledger APIs

- `POST /api/loan-ledger`  
  Create loan ledger entry (debit/credit)

- `GET /api/loan-ledger`  
  Retrieve all loan ledger records

---

## 🔹 Transactions APIs

- `POST /api/transactions`  
  Create accounting transaction entry

- `GET /api/transactions`  
  Retrieve all transaction records

---

## 🔹 Static  APIs (DEV / TEST only)

- `POST /api/static/init`  
  Insert initial demo/sample data

> ⚠️ This endpoint is intended only for development or testing purposes and should be disabled in production.

---

## 🔄 Transaction Management

Critical business operations are wrapped using:

```java
@Transactional
````

This guarantees:

* Atomic operations
* Data consistency across tables
* Automatic rollback on failure
* Reliable accounting integrity

---

## 📂 Project Structure

```
src/main/java
 └── com.swosti
     └── v1
         ├── controller
         │    └── swosti
         │         ├── DisburseController
         │         ├── LoanLedgerController
         │         ├── TransactionsController
         │         └── StaticController
         ├── service
         │    └── swosti
         │         ├── DisburseService
         │         ├── LoanLedgerService
         │         ├── TransactionsService
         │         └── StaticService
         ├── repository
         │    └── swosti
         └── model
              └── swosti
```

---

## 🚀 How to Run the Project

1. Clone the repository or extract the ZIP file
2. Create a PostgreSQL database
3. Update database credentials in `application.properties`
4. Build and run the project:

```bash
mvn clean install
mvn spring-boot:run
```

5. Access APIs via:

```
http://localhost:3090
```

---

## 🎯 Purpose of This Project

This project demonstrates:

* Strong understanding of **SOLID principles**
* Clean separation of domain responsibilities
* Transaction-safe accounting logic
* Professional REST API design
* Maintainable and extensible backend architecture
* Real-world enterprise development practices

---

## 👤 Author

**Saddam Hossen**
Software Programmer Candidate

📧 Email: [saddamh.dev@gmail.com](mailto:saddamh.dev@gmail.com)
📞 Mobile: 01647618952

---

```

