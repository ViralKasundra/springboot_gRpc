package com.example.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Random;

@GrpcService
public class StockService extends StockTickerGrpc.StockTickerImplBase {

    private final Random random = new Random();

    @Override
    public void streamStockPrices(StockRequest request, StreamObserver<StockResponse> responseObserver) {
        String symbol = request.getSymbol().toUpperCase();

        for (int i = 0; i < 5; i++) {
            double price = 100 + random.nextDouble() * 50;
            StockResponse response = StockResponse.newBuilder()
                    .setSymbol(symbol)
                    .setPrice(price)
                    .build();
            responseObserver.onNext(response);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                responseObserver.onError(e);
                return;
            }
        }

        responseObserver.onCompleted();
    }
}
