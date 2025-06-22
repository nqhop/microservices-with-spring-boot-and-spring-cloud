package com.example.magnus;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {
    private final StreamBridge streamBridge;

    public MessagePublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishMessage(String message) {
        streamBridge.send("test-out", message);
    }
}
