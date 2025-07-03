📈 Stock Ticker - Server-Side Streaming with gRPC and Spring Boot (Java 11)
This project demonstrates how to implement server-side streaming using gRPC in a Spring Boot application with Java 11. The use case simulates a stock ticker that streams real-time stock prices to the client.

1.🔧 Technologies Used
Java 11
Spring Boot 2.7.x
gRPC (1.54.0)
Protocol Buffers (proto3)
Maven
WebFlux + Server-Sent Events (SSE) for REST integration
2.Generate gRPC Java Code mvn clean compile

3. Run the Application mvn spring-boot:run

📡 API Endpoints 🔁 Server-Side Streaming via gRPC gRPC Method: rpc StreamStockPrices (StockRequest) returns (stream StockResponse); Sends one StockRequest with stock symbol Receives multiple StockResponse messages with random prices

🌐 REST to gRPC Streaming via SSE GET - http://localhost:8080/api/stream?symbol=AAPL
