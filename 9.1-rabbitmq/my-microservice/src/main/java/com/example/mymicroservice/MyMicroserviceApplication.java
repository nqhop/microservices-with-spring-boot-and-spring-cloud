package com.example.mymicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class MyMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMicroserviceApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello ftom my service";
    }
}
