package com.example.bookstore.backend.user.backend_user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BackendUserApplication {

	private static final Logger LOG = LoggerFactory.getLogger(BackendUserApplication.class);
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BackendUserApplication.class);
		app.addListeners((ApplicationListener<ContextClosedEvent>) event ->
				LOG.info("===========================\nApplication shutting down: " + event.getTimestamp())
		);
		app.run(args);
	}
}
