# Bank System Application

## Overview

This is a simple desktop banking application built in Java Swing. It allows users to register a new account, log in, view personal and account information, and log out. All data is stored locally in a JSON file (`atm_online_useri.json`) using the Gson library.

## Features

* **User Registration**

  * Collects personal details: first name, last name, CNP, email, phone, address, username, and password.
  * Automatically generates a unique account number and expiration date.
  * Saves new accounts to a JSON file, preserving all existing accounts.

* **Login**

  * Users can log in with their username and password.
  * Displays error messages for invalid credentials or missing data file.
  * After a successful login, the main dashboard appears.

* **Dashboard (Logged-In Page)**

  * Shows user’s username, account number, and balance in a bordered panel.
  * “MyInfo” button: Displays a modal dialog with personal information (first name, last name, email, phone, address).
  * “Account info” button: Displays a modal dialog with account balance.
  * “Logout” button: Asks for confirmation before logging out and returning to the login screen.

* **Local JSON Storage**

  * All account data is saved in `atm_online_useri.json` as an array of account objects.
  * Uses Gson with custom type adapters for `LocalDate` serialization and deserialization.
  * On startup or registration, the application reads from and writes to this JSON file.

## Technologies & Libraries

* **Java SE 11+**
* **Swing** for GUI components
* **Gson 2.10.1** for JSON serialization/deserialization
* **LocalDate** (java.time) for handling expiration dates
* **File I/O** (java.io, java.nio.file) for reading and writing JSON files

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   ├── org.example.Backend/
│   │   │   ├── ATM_online.java         // Login logic, JSON parsing
│   │   │   ├── BankAccount.java       // Account model (number, user, balance, expireDate)
│   │   │   ├── User.java              // User model (CNP, fName, lName, email, phone, address, username, password)
│   │   │   └── GsonFactory.java       // Configures Gson with LocalDate adapters
│   │   └── org.example.UI/
│   │       ├── MainFrame.java         // Application entry point (JFrame)
│   │       ├── ATM_loginPanel.java    // Login screen
│   │       ├── ATM_createAccount.java // Registration screen
│   │       └── ATM_loggedPage.java    // Dashboard screen (user/account info, logout)
│   └── resources/
│       └── atm_online_useri.json      // Local JSON file storing account data
└── test/
    └── java/                         // (Optional) Unit tests
```

## Setup & Running

1. **Clone the repository**

   ```bash
   git clone <repository_url>
   cd bank-system
   ```

2. **Ensure you have Java 11 or later installed**
   Verify with:

   ```bash
   java -version
   ```

3. **Build & Run**

   * If using Maven:

     ```bash
     mvn clean compile
     mvn exec:java -Dexec.mainClass="org.example.UI.MainFrame"
     ```
   * If using an IDE (IntelliJ IDEA, Eclipse):
     Import as a Maven project, then run `org.example.UI.MainFrame` as a Java application.

4. **Dependencies**
   The project uses:

   ```xml
   <dependency>
     <groupId>com.google.code.gson</groupId>
     <artifactId>gson</artifactId>
     <version>2.10.1</version>
   </dependency>
   ```

## Usage

1. **Registration**

   * Click **Register** on the login screen.
   * Fill in all fields (first name, last name, CNP, email, phone, address, username, password).
   * Click **Create Account**.
   * A confirmation dialog will show your generated account number.
   * You’ll be redirected back to the login screen.

2. **Login**

   * Enter the username and password you registered with.
   * Click **Login**.
   * If credentials are correct, the dashboard opens. Otherwise, an error message is shown.

3. **Dashboard**

   * A bordered panel displays your **username**, **account number**, and **balance**.
   * Click **MyInfo** to view personal details (modal dialog).
   * Click **Account info** to view your current balance (modal dialog).
   * Click **Logout** to return to the login screen (with a “Are you sure?” confirmation).

## JSON File Format (`atm_online_useri.json`)

The JSON file stores an array of `BankAccount` objects. Example:

```json
[
  {
    "number": 123456,
    "user": {
      "CNP": "1960501123456",
      "fName": "Ioan",
      "lName": "Popescu",
      "email": "ion.popescu@example.com",
      "phone": "0712345678",
      "address": "Strada Exemplu, Nr. 1",
      "username": "popescu",
      "password": "securePass"
    },
    "balance": 0.0,
    "expireDate": "2028-03-12"
  },
  {
    "number": 654321,
    "user": {
      "CNP": "2900702123456",
      "fName": "Maria",
      "lName": "Ionescu",
      "email": "maria.ionescu@example.com",
      "phone": "0723456789",
      "address": "Bulevardul Exemplar, Nr. 2",
      "username": "ionescu",
      "password": "anotherPass"
    },
    "balance": 250.50,
    "expireDate": "2028-03-12"
  }
]
```

* **number**: Unique account number (long).
* **user**: Object containing personal details and credentials.
* **balance**: Current account balance (double).
* **expireDate**: Expiration date as `"YYYY-MM-DD"`.

## Notes

* Passwords are stored in plain text in JSON for simplicity; in production, use secure hashing.
* `expireDate` is serialized/deserialized via custom Gson adapters in `GsonFactory`.
* All file I/O exceptions are handled with dialog alerts.

