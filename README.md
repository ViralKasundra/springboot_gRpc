grpc-bidirectional-chat/
├── pom.xml
├── src
│   ├── main
│   │   ├── java/com/example/grpc/
│   │   │   ├── ChatServiceImpl.java       # gRPC service impl
│   │   │   └── GrpcServerApplication.java # Spring Boot entry
│   │   └── resources/
│   │       └── application.yml
│   └── proto/
│       └── chat.proto                     # gRPC contract

grpc-bidirectional-chat-client/
├── pom.xml
├── src
│   ├── main
│   │   ├── java/com/example/grpc/
│   │   │   ├── ChatController.java        # REST controller
│   │   │   └── GrpcClientApplication.java # Spring Boot entry
│   │   └── resources/
│   │       └── application.yml
