This project demonstrates client-side streaming using gRPC with a Spring Boot application. It includes:

A gRPC server that receives file chunks via stream and reconstructs them.

A REST client that accepts multipart/form-data uploads and streams the file to the server over gRPC.

****🧪 How to Run (Summary)****

**1. Start gRPC Server**

cd grpc-client-streaming-upload
mvn spring-boot:run

**2. Start REST Client**

cd grpc-client-streaming-upload-client
mvn spring-boot:run

**3. Upload a File via REST**

curl -X POST http://localhost:8080/upload \
  -H "Content-Type: multipart/form-data" \
  -F "file=@/path/to/your/file.txt"
