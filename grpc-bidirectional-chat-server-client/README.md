**📦 Bidirectional Chat App (Spring Boot + gRPC + Java 11)**
A real-world example of bidirectional streaming using gRPC in a Spring Boot application with a REST wrapper for client interaction.

****🧪 How to Run****
**📦 Server**
cd grpc-bidirectional-chat
mvn clean install
mvn spring-boot:run


**📦 Client (REST Wrapper)**
cd grpc-bidirectional-chat-client
mvn clean install
mvn spring-boot:run

****🧪 Sample cURL Test****
curl -X POST "http://localhost:8081/chat?sender=Alice&message=Hello"
