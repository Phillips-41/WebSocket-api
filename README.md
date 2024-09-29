
# CoinDCX WebSocket Trading Application

## Description
This is a Java application that connects to the CoinDCX WebSocket API to fetch real-time trading data. It prepares order payloads based on user-defined trigger prices, allowing for simulated trading operations.

## Libraries Used
- **Spring Boot**: Framework for building the application.
- **Java-WebSocket**: Library for WebSocket communication.
- **Gson**: Library for JSON parsing and serialization.
- **Lombok**: Library for reducing boilerplate code in Java classes.

## Setup Instructions
1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```
2. **Build the Project: Navigate to the project       directory and build the project using Maven:**:

```bash
  mvn clean install
```
3. **Run the Application: After a successful build, run the application using the following Maven command:**:
```bash
  mvn spring-boot:run
```
4. **Running from Executable JAR: If you prefer to run the application as a standalone JAR:**:
```bash
  java -jar target/coin-dcx-websocket-0.0.1-SNAPSHOT.jar
```
## Operation Instructions

1. **Starting the Application: Once the application starts, you’ll see the following prompt in the terminal:**:
```bash
  Welcome to the CoinDCX Trading CLI
  Please enter the trigger price:
```
2. **Enter the Trigger Price: Input a trigger price in USD for BTC (e.g., 45000):**:
```bash
  Please enter the trigger price: 45000
```
3. **Market Price Monitoring: The application will begin monitoring the BTC/USDT market price. Once the current market price reaches the trigger price or beyond, it will simulate the preparation of a buy/sell order payload and display the results in the terminal:**:
```bash
Trigger price set: 45000.0
Current market price is: 46000.0
Buy Order Prepared: OrderPayload(type=buy, pair=BTCUSDT, price=46000.0, quantity=1.0)
```
4. **Error Handling:**:
- If the WebSocket connection fails or the data cannot be fetched, appropriate error messages will be displayed in the terminal.
- Logs will also capture the state of WebSocket connections and any problems encountered.

## Configuration
- WebSocket URI: The application connects to CoinDCX WebSocket API using this URI
```bash
  URI url = new URI("wss://stream.coindcx.com");
```
## Libraries Used
- Spring Boot: Simplifies application configuration and dependency management.
- Java-WebSocket Client: Handles WebSocket communication with CoinDCX API.
- Gson: Parses JSON data received from WebSocket.
- Lombok: Reduces boilerplate code (e.g., getters, setters).
- Spring Boot DevTools: Provides live reloading for quick development feedback.
