package com.example.magnus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;


@SpringBootApplication
public class Application {

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Supplier<String> stringSupplier() {
		return () -> "Hello from ID: " + UUID.randomUUID();
	}

	@Bean
	public Consumer<String> stringConsumer() {
		return message -> LOG.info("🔔 Received message: {}", message);
	}
}
