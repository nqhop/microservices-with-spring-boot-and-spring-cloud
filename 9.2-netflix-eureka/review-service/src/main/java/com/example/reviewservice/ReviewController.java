package com.example.reviewservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class ReviewController {

    @GetMapping("/")
    public String home() throws UnknownHostException {
        return "Hello from " + InetAddress.getLocalHost().getHostName();
    }
}
