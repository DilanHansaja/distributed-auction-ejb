# Real-Time Distributed Online Auction System

![Java EE](https://img.shields.io/badge/Java%20EE-Jakarta%20EE-red)
![EJB](https://img.shields.io/badge/Architecture-EJB%20%26%20JMS-blue)
![Concurrency](https://img.shields.io/badge/Concurrency-Thread%20Safe-green)
![Status](https://img.shields.io/badge/Status-Completed-success)

A high-concurrency, distributed auction platform built with **Java EE**. This system is engineered to handle real-time simultaneous bidding from multiple users without data conflicts, utilizing **Enterprise JavaBeans (EJB)**, **JMS**, and **WebSockets**.

## 🎥 Demo
**[▶️ Click here to watch the system in action](https://www.linkedin.com/posts/dilan-hansaja_javaee-ejb-jms-activity-7341510821656612865-Cxp2?utm_source=social_share_send&utm_medium=member_desktop_web&rcm=ACoAADkjDw4BlVmrCmNEv0_m6RsDd3513MCko2E)**
*(Simulating high-concurrency bidding with JMeter and real-time updates)*

---

## 🏗 High-Concurrency Architecture Flow
The following diagram illustrates how the system handles simultaneous bids using **Container-Managed Concurrency** and **Asynchronous Messaging**.
![High Concurrency Architecture Diagram](images/architecture_diagram.png)

---

## 🏛 Architectural Decisions: Why No Database?

You might notice this project uses **In-Memory Storage** (`ConcurrentHashMap`) instead of a traditional relational database. This was a deliberate architectural choice.

The primary goal of this engineering project was to demonstrate deep knowledge of **concurrency control** and **state management** at the application layer. By managing state in memory:

- I implemented strict **Container-Managed Concurrency** manually using Singleton EJBs.
- I utilized explicit **Read/Write Locking** strategies (`@Lock(LockType.WRITE)`) to prevent race conditions during simultaneous bids.
- This approach isolates the study of **thread safety mechanisms** in Java EE without offloading locking responsibility to a database engine.

---

## Key Features

- **Real-Time Push Notifications**: Uses JMS (Java Message Service) and Message-Driven Beans (MDB) to decouple bid processing from user notifications. Updates are pushed to clients via WebSockets instantly.

- **Thread-Safe Bidding**: A `@Singleton` BidMapBean manages the shared state of all auctions, ensuring data consistency even when hundreds of users bid at the exact same millisecond.

- **Automatic Auction Lifecycle**: Utilizes `ScheduledExecutorService` to automatically close auctions when time expires and notify all connected clients.

- **Scalable Design**: Separates business logic (Stateless Beans) from shared state (Singleton Beans) to optimize performance.

---

## Configuration & Performance Tuning

To achieve high concurrency (tested with JMeter load tests), the GlassFish Server thread pools must be tuned beyond default settings to handle thousands of concurrent requests.

### 1. EJB Container Settings

- **Initial & Min Pool Size**: 200
- **Max Pool Size**: 3000
- **Pool Idle Timeout**: 60 Seconds

### 2. JMS Connection Factory

- **Initial & Min Pool Size**: 50
- **Max Pool Size**: 500
- **Max Wait Time**: 30000 Milliseconds

### 3. Web Container (Thread Pool)

- **Max Queue Size**: 10000
- **Max Thread Pool Size**: 3000
- **Min Thread Pool Size**: 200

---

## Getting Started

### Prerequisites
- JDK 8 or higher
- GlassFish Server / Payara Server
- NetBeans or IntelliJ IDEA

### Installation
1. Clone the repository.
2. Open the project in your IDE.
3. Configure your GlassFish server with the Pool Settings listed above for best performance.
4. Build and Run the project (Deploy the EAR/WAR).

---

## Accessing the Admin Panel

To test the administrative features (creating auctions, managing users), use the following credentials:

- **Username**: `admin`
- **Password**: `1234`

---

## 🛠 Technology Stack

- **Backend**: Java EE (EJB 3.x, JMS, Servlets)
- **Middleware**: GlassFish Server
- **Concurrency**: Java Concurrency API (ConcurrentHashMap, Locks)
- **Frontend**: JSP, WebSocket API
- **Testing**: Apache JMeter (Load Testing)

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.
