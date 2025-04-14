# 🪙 Crypto Wallet Microservices

A modular microservice-based application that allows users to track their cryptocurrency holdings, live prices, and evaluate portfolio performance.

## 🧱 Tech Stack

- Java 17
- Spring Boot 3
- Spring Cloud (Eureka, Feign, Gateway)
- Maven
- Docker & Docker Compose
- H2 (in-memory database)
- CoinCap API
- Postman (for testing)

---

## 🚀 How to Run

### 1. Clone the repository

```bash
git clone https://github.com/mfatihkan13/crypto-wallet-app.git
cd crypto-wallet-app
```

### 2. Build all services with Maven

```bash
mvn clean install -DskipTests

```

### 3. Start all services with Docker Compose

```bash
docker compose up --build

```

### 🚀 Services and Ports

This will start the following services using **Docker Compose**:

| Service             | Port  |
|---------------------|-------|
| Discovery Server    | 8761  |
| API Gateway         | 8080  |
| Wallet Service      | 8084  |
| Asset Service       | 8081  |
| Price Service       | 8083  |
| Evaluation Service  | 8082  |
