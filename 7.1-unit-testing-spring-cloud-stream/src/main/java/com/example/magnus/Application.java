package com.example.magnus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;


@SpringBootApplication
@RestController
public class Application {

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	private final MessagePublisher messagePublisher;

	public Application(MessagePublisher messagePublisher) {
		this.messagePublisher = messagePublisher;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostMapping("/publish")
	public String publishMessage(@RequestParam String message){
		messagePublisher.publishMessage(message);
		return "✅ Sent: " + message;
	}

	@Bean
	public Consumer<String> messageConsumer() {
		return message -> LOG.info("🔔 Received message: {}", message);
	}
}
