package com.example.grpc;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class StockController {

    private final StockClient stockClient;

    public StockController(StockClient stockClient) {
        this.stockClient = stockClient;
    }

    @GetMapping(value = "/api/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamStockPrices(@RequestParam String symbol) {
        return stockClient.streamPrices(symbol);
    }
}
