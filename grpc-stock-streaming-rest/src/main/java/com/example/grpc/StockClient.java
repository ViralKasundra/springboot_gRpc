package com.example.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Service
public class StockClient {

    public Flux<String> streamPrices(String symbol) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        StockTickerGrpc.StockTickerStub stub = StockTickerGrpc.newStub(channel);
        StockRequest request = StockRequest.newBuilder().setSymbol(symbol).build();

        return Flux.create(emitter -> {
            stub.streamStockPrices(request, new StreamObserver<StockResponse>() {
                @Override
                public void onNext(StockResponse response) {
                    emitter.next("Price of " + response.getSymbol() + ": $" + response.getPrice());
                }

                @Override
                public void onError(Throwable t) {
                    emitter.error(t);
                }

                @Override
                public void onCompleted() {
                    emitter.complete();
                    channel.shutdown();
                }
            });
        }, FluxSink.OverflowStrategy.BUFFER);
    }
}
