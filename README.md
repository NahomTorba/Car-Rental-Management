# Car Rental Management System

A comprehensive Java-based car rental management system that provides a console-based interface for managing car rentals, customers, and rental transactions. The system uses MySQL database for data persistence and implements object-oriented design principles with proper separation of concerns.

## 🚗 Features

- **Car Management**: Add and manage car inventory
- **Customer Management**: Register and manage customer information
- **Rental Operations**: Rent and return cars with automatic availability tracking
- **Transaction Logging**: Automatic logging of all rental activities
- **Database Integration**: MySQL database for persistent data storage
- **Exception Handling**: Comprehensive error handling with custom exceptions

## 🏗️ Project Architecture

The project follows a layered architecture with clear separation of concerns:

### Project Structure
```
src/main/java/project/
├── abstracts/
│   └── Vehicle.java              # Abstract base class for all vehicles
├── dao/
│   ├── CarDao.java               # Data Access Object for car operations
│   ├── CustomerDao.java          # Data Access Object for customer operations
│   └── DBConnection.java         # Database connection management
├── interfaces/
│   ├── Rentable.java             # Interface for rentable items
│   └── Returnable.java           # Interface for returnable items
├── model/
│   ├── Car.java                  # Car entity extending Vehicle
│   ├── Customer.java             # Customer entity
│   └── Rental.java               # Rental transaction entity
├── service/
│   └── RentalService.java        # Business logic layer
├── ui/
│   └── MainMenu.java             # User interface and menu system
└── util/
    ├── CarNotAvailableException.java
    ├── CarNotFoundException.java
    ├── CustomerNotFoundException.java
    └── FileLogger.java           # Logging utility
```

## 📋 Prerequisites

- **Java Development Kit (JDK)**: Version 8 or higher
- **MySQL Database**: Version 5.7 or higher
- **MySQL JDBC Driver**: mysql-connector-java.jar

## 🛠️ Setup Instructions

### 1. Database Setup

1. **Install MySQL** if not already installed
2. **Create the database**:
   ```sql
   CREATE DATABASE car_rental_db;
   ```
3. **Create the required tables**:
   ```sql
   -- Customer Table
   CREATE TABLE customers (
       customer_id VARCHAR(50) PRIMARY KEY,
       name VARCHAR(100) NOT NULL
   );

   -- Vehicle Table (abstract base class for Car, so we store general vehicle info here)
   CREATE TABLE vehicles (
       vehicle_id VARCHAR(50) PRIMARY KEY,
       model VARCHAR(100) NOT NULL,
       is_available BOOLEAN DEFAULT TRUE
   );

   -- Rental Table (associates Customer with Car for a number of days)
   CREATE TABLE rentals (
       rental_id INT AUTO_INCREMENT PRIMARY KEY,
       customer_id VARCHAR(50),
       vehicle_id VARCHAR(50),
       rental_days INT NOT NULL,
       rental_date DATE DEFAULT CURRENT_DATE,

       FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
       FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id)
   );
   ```
4. **Configure database connection** by editing `src/main/resources/dbconfig.properties`:
   ```properties
   db.url=jdbc:mysql://localhost:3306/car_rental_db?useSSL=false&serverTimezone=UTC
   db.user=your_username
   db.password=your_password
   ```

### 2. Download MySQL JDBC Driver

1. Download `mysql-connector-java.jar` from [MySQL Downloads](https://dev.mysql.com/downloads/connector/j/)
2. Place the JAR file in your project root directory

### 3. Compile and Run

#### Using Command Line:

1. **Navigate to project directory**:
   ```bash
   cd "path/to/Car Rental Management"
   ```

2. **Compile the project**:
   ```bash
   javac -cp ".;mysql-connector-java.jar" -d out src/main/java/project/**/*.java src/main/Main.java
   ```

3. **Run the application**:
   ```bash
   java -cp "out;mysql-connector-java.jar" main.Main
   ```

#### Using IDE:

1. **Open the project** in your preferred IDE (IntelliJ IDEA, Eclipse, VS Code)
2. **Add MySQL JDBC driver** to your project dependencies
3. **Run the `Main.java`** file

## 🎯 Usage Guide

### Main Menu Options

The application provides a console-based menu with the following options:

#### 1. Add Car
- **Purpose**: Add a new car to the rental inventory
- **Inputs**: 
  - Car ID (unique identifier)
  - Car Model (e.g., "Toyota Camry")
- **Example**:
  ```
  Enter Car ID: CAR001
  Enter Car Model: Toyota Camry
  Car added successfully.
  ```

#### 2. Register Customer
- **Purpose**: Register a new customer in the system
- **Inputs**:
  - Customer ID (unique identifier)
  - Customer Name
- **Example**:
  ```
  Enter Customer ID: CUST001
  Enter Customer Name: John Doe
  Customer registered successfully.
  ```

#### 3. Rent Car
- **Purpose**: Rent a car to a customer
- **Inputs**:
  - Customer ID
  - Car ID
  - Number of rental days
- **Business Rules**:
  - Customer must be registered
  - Car must exist and be available
  - Rental days must be positive
- **Example**:
  ```
  Enter Customer ID: CUST001
  Enter Car ID: CAR001
  Enter Number of Rental Days: 3
  Rental successful: John Doe rented Toyota Camry for 3 day(s). Cost: $1500.0
  ```

#### 4. Return Car
- **Purpose**: Return a rented car
- **Inputs**:
  - Car ID
- **Example**:
  ```
  Enter Car ID to return: CAR001
  Car returned successfully.
  ```

#### 5. View All Cars
- **Purpose**: Display all cars in the inventory with their availability status
- **Output Format**: `CarID - Model - Status`

#### 6. View All Customers
- **Purpose**: Display all registered customers
- **Output Format**: `CustomerID - Name`

#### 7. View All Rentals
- **Purpose**: Display all current rental transactions
- **Output Format**: `Customer rented Car for X day(s). Cost: $Y`

## 🔧 Key Classes and Their Responsibilities

### Model Classes
- **`Car`**: Extends `Vehicle` and implements `Rentable` and `Returnable` interfaces
- **`Customer`**: Represents customer information
- **`Rental`**: Manages rental transactions with cost calculation

### Service Layer
- **`RentalService`**: Contains business logic for all rental operations
- **`FileLogger`**: Handles logging of rental activities to `rental_log.txt`

### Data Access Layer
- **`CarDao`**: Database operations for car management
- **`CustomerDao`**: Database operations for customer management
- **`DBConnection`**: Manages database connections using properties file

### User Interface
- **`MainMenu`**: Console-based user interface with menu-driven navigation

## 📊 Database Schema

The system uses the following database tables (created automatically by DAO classes):
- **cars**: Stores car information (id, model, availability)
- **customers**: Stores customer information (id, name)
- **rentals**: Stores rental transactions (car_id, customer_id, days, cost)

## 🚨 Exception Handling

The system includes custom exceptions for better error handling:
- **`CarNotFoundException`**: Thrown when a car doesn't exist
- **`CustomerNotFoundException`**: Thrown when a customer isn't registered
- **`CarNotAvailableException`**: Thrown when trying to rent an unavailable car

## 📝 Logging

All rental activities are automatically logged to `rental_log.txt` with timestamps:
```
2025-08-01T22:33:11.191984600 - Customer 001 rented car 001 for 3 days.
```

## 🎨 Design Patterns Used

- **DAO Pattern**: Data Access Objects for database operations
- **Service Layer Pattern**: Business logic separation
- **Interface Segregation**: `Rentable` and `Returnable` interfaces
- **Abstract Class**: `Vehicle` as base class for different vehicle types
- **Exception Handling**: Custom exceptions for specific error scenarios

## 🔍 Troubleshooting

### Common Issues:

1. **Database Connection Failed**:
   - Verify MySQL is running
   - Check `dbconfig.properties` credentials
   - Ensure database `car_rental_db` exists

2. **ClassNotFoundException for MySQL Driver**:
   - Ensure `mysql-connector-java.jar` is in classpath
   - Verify JAR file is not corrupted

3. **Compilation Errors**:
   - Ensure JDK 8+ is installed
   - Check all source files are present
   - Verify package structure matches directory structure

## 🤝 Team Contributions

| Team Member | Contribution Area |
|-------------|-------------------|
| **Nahom**   | Database design and Connection, abstract class, car model, interface creation |
| **Sofoniyas** | Rental Model, MainMenu, Rental Service |
| **Nardos**  | File logger, DAO implementations, Car Exceptions handling |
| **Tesfaye** | Customer Model, Customer Exception handling | 

## 📄 License

This project is for educational purposes and demonstrates object-oriented programming concepts in Java.

---

**Note**: This is a console-based application.

---
