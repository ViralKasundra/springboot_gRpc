package com.example.grpc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreeterController {

    private final GreeterClient greeterClient;

    public GreeterController(GreeterClient greeterClient) {
        this.greeterClient = greeterClient;
    }

    @GetMapping("/api/greet")
    public String greet(@RequestParam String name) {
        return greeterClient.sayHello(name);
    }
}
