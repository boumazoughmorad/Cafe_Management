# Cafe Management System

## Overview
The **Cafe Management System** is a comprehensive solution designed to streamline operations for both cafe owners and customers. It allows customers to reserve tables, order products (such as juices, snacks, and other items), and download their bills. Cafe owners can efficiently manage reservations, track cafe statistics, and monitor business performance.

### Key Features
- **Table Reservations**: Customers can reserve tables and place orders for various products.
- **Bill Download**: Customers can download their bills after placing orders.
- **Reservation History**: Customers can view their past reservations.
- **Cafe Statistics**: Cafe owners can access detailed statistics about cafe operations.
- **Secure Application**: Utilizes **Spring Security** to ensure the application is secure.
- **Performance Optimization**: Uses **useMemo** and **useCallback** to optimize frontend performance and prevent unnecessary re-renders.
- **LocalStorage Caching**: Caches specific data in `localStorage` to reduce API calls and improve performance.

---

## Technologies Used
- **Backend**: Spring Boot
- **Frontend**: ReactJS
- **Database**: MySQL
- **Security**: Spring Security
- **Frontend Optimization**: useMemo, useCallback

---

## Installation

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- Node.js (for ReactJS)
- MySQL Database
- Maven (for Spring Boot)

### Steps to Set Up the Project

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/boumazoughmorad/Cafe_Management.git
   cd cafe-management-system

2. **Backend (Spring Boot) Setup:***
1. Navigate to the `backend` folder:
   ```bash
   cd Cafe_Management_system_BackEnd/Cafe_Management
```
2.Build the project using Maven::
   ```bash
   mvn clean install
```
3. Run the Spring Boot application::
   ```bash
   mvn spring-boot:run
```

3. **Frontend (ReactJS) Setup:***
1. Navigate to the frontend folder::
   ```bash
   cd cafe_management_FrontEnd
```
2.Install dependencies::
   ```bash
  npm install
```
3. Start the React development server:
   ```bash
   npm start
```

4. **Database Setup:***
1. Ensure MySQL is running locally or on a cloud service.

2. Update the application.properties file in the backend folder with your MySQL database connection details:
   ```bash
  spring.datasource.url=jdbc:mysql://localhost:3306/cafe
spring.datasource.username=root
spring.datasource.password=
```
4. **Some images emphasizing the application interface:***
![Image Alt Text](/image.jpg)

![Image Alt Text](/image2.jpg)

![Image Alt Text](/image3.jpg)

![Image Alt Text](s/image4.jpg)

![Image Alt Text](/image5.jpg)

![Image Alt Text](/image6.jpg)
![Image Alt Text](/image7.jpg)
