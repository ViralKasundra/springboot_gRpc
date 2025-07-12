package com.example.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @GrpcClient("grpc-bidirectional-chat")
    private ChatServiceGrpc.ChatServiceStub asyncStub;

    @PostMapping
    public ResponseEntity<String> chat(@RequestParam String sender, @RequestParam String message) {
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<ChatMessage> requestObserver = asyncStub.chatStream(new StreamObserver<>() {
            @Override
            public void onNext(ChatMessage response) {
                System.out.println("Received from server: " + response.getMessage());
                latch.countDown();
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Stream completed");
                latch.countDown();
            }
        });

        ChatMessage req = ChatMessage.newBuilder()
            .setSender(sender)
            .setMessage(message)
            .setTimestamp(System.currentTimeMillis())
            .build();

        requestObserver.onNext(req);
        requestObserver.onCompleted();

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return ResponseEntity.ok("Message sent to gRPC server");
    }
}
