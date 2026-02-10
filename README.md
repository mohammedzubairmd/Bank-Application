# 🏦 Bank-Application: Secure Java Banking Suite

A professional-grade, console-based banking application designed to handle core financial operations with high data integrity. This project implements a full **DAO (Data Access Object) architecture** to manage user accounts and financial transactions using **Java** and **MySQL**.

## 🚀 Key Features

* **Dynamic Account Creation:** Automatically generates unique account numbers starting from `1001`.
* **Atomic Fund Transfers:** Executes multi-step transfers between accounts using SQL transactions to ensure money is never "lost" during a failure.
* **Real-time Ledger:** Every deposit, withdrawal, and transfer is logged into a dedicated transaction history table.
* **Mini Statement Engine:** Generates a formatted summary of the 5 most recent transactions for any account.
* **Balance Monitoring:** Instant retrieval of real-time account balances using precise `BigDecimal` math.
* **Database Inspection:** Built-in metadata utility to verify database connectivity and schema structure.

## 🛠️ Tech Stack

**Backend:**
* **Language:** Java (JDK 8+)
* **Database:** MySQL 8.0
* **API:** JDBC (Java Database Connectivity)

**Architecture:**
* **Layered Design:** Separated into UI, Business Logic (Service), and Data Access (DAO) layers for better maintainability.

---

## 📸 System Preview

### Main Menu Interface
```text
------------------------------------------
      INITIALIZING MDZ BANK         
------------------------------------------

--- ZUBAIR BANK MAIN MENU ---
0. Open New Account
1. Deposit Money
2. Withdraw Money
3. Check Balance
4. Mini Statement
5. Transfer Money
6. Exit
Please enter your choice: _
```
# Mini Statement Output
```text
Plaintext
============================================================
                ZUBAIR BANK - MINI STATEMENT                
============================================================
Date         | Type         | Amount     | Balance   
------------------------------------------------------------
2026-02-10   | Opening Dep  | 1000.00    | 1000.00   
2026-02-10   | Transfer Out | 200.00     | 800.00    
2026-02-10   | Transfer In  | 50.00      | 850.00    
============================================================
```
🏗️ Database Schema
To run this project, execute the following SQL in your MySQL workbench:
```text
SQL
CREATE DATABASE bank;
USE bank;

-- Table for User Accounts
CREATE TABLE accountmaster (
    accno INT PRIMARY KEY,
    name VARCHAR(100),
    address VARCHAR(255),
    date DATE,
    pan VARCHAR(20),
    phone VARCHAR(15),
    email VARCHAR(100),
    balance DECIMAL(15, 2)
);

-- Table for Transaction History
CREATE TABLE transaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    accno INT,
    date DATE,
    type VARCHAR(20),
    amount DECIMAL(15, 2),
    balance DECIMAL(15, 2),
    FOREIGN KEY (accno) REFERENCES accountmaster(accno)
);
```
⚙️ Installation & Setup
Clone the repository:

Bash
git clone [https://github.com/mohammedzubairmd/Bank-Application.git](https://github.com/mohammedzubairmd/Bank-Application.git)
Configure Database Credentials: Open getConnection.java and update the connection details to match your MySQL setup:

Java
private static final String URL = "jdbc:mysql://localhost:3306/bank";
private static final String USER = "root";
private static final String PASS = "Your_Password_Here";
Add MySQL Connector: Ensure you have the mysql-connector-j JAR file in your classpath.

Compile and Run:

Bash
```text
javac *.java
java Bank
```
📂 Project Structure
```text
Bank.java: Entry point containing the main method.

BankUI.java: Handles user input and menu navigation.

BankBusinessService.java: Logic for deposits, withdrawals, and transfers.

AccountMasterDAO.java & TransactionDAO.java: Direct database interactions.

accountMaster.java & transaction.java: Data models (POJOs).

getConnection.java: Database connection utility.

metaData.java: Utility to print database schema info.
```

Developed by: Mohammed Zubair | Date: 2026

