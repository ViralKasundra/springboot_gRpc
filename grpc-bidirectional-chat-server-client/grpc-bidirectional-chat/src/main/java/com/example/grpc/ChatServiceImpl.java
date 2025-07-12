package com.example.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GRpcService;

@GRpcService
public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {

    @Override
    public StreamObserver<ChatMessage> chatStream(StreamObserver<ChatMessage> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(ChatMessage msg) {
                ChatMessage reply = ChatMessage.newBuilder()
                    .setSender("Server")
                    .setMessage("Reply to: " + msg.getMessage())
                    .setTimestamp(System.currentTimeMillis())
                    .build();
                responseObserver.onNext(reply);
            }

            @Override
            public void onError(Throwable t) { }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
